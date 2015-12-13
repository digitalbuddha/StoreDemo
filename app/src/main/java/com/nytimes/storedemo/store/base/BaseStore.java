package com.nytimes.storedemo.store.base;

import android.os.Debug;
import android.support.annotation.NonNull;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.nytimes.storedemo.store.util.Id;
import com.nytimes.storedemo.store.util.OnErrorResumeWithEmpty;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Store to be used for loading an object different data sources
 *
 * @param <Raw>    data type before parsing
 * @param <Parsed> data type after parsing
 *                 <p/>
 *                 Example usage:  {@link }
 */
public abstract class BaseStore<Raw, Parsed> {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseStore.class);

    //Servers to prevent network request duplications
    protected final Cache<Id<Parsed>, Observable<Parsed>> inFlightRequests;

    //Memory cache
    private final Cache<Id<Parsed>, Parsed> memory;

    public BaseStore() {
        StoreClerk.register(this);
        inFlightRequests = CacheBuilder.newBuilder()
                .expireAfterWrite(TimeUnit.MINUTES.toMillis(1), TimeUnit.MILLISECONDS)
                .build();
        memory = CacheBuilder.newBuilder()
                .maximumSize(getCacheSize())
                .expireAfterAccess(getCacheTTL(), TimeUnit.MILLISECONDS)
                .build();
    }

    /**
     * @param id
     * @return an observable from the first data source that is available
     */
    protected Observable<Parsed> get(@NonNull final Id<Parsed> id) {
//        LOGGER.info("Getting from Store");

        return Observable.concat(
                memory(id),
                disk(id),
                network(id)
        ).first();
    }

    /**
     * @return data from memory
     */
    protected Observable<Parsed> memory(@NonNull final Id<Parsed> id) {
        return Observable
                .defer(new Func0<Observable<Parsed>>() {
                    @Override
                    public Observable<Parsed> call() {
//                        LOGGER.info("Getting  from memory");
                        Parsed data = memory.getIfPresent(id);
                        return data == null ? Observable.<Parsed>empty() : Observable.just(data);
                    }
                })
                .onErrorResumeNext(new OnErrorResumeWithEmpty<Parsed>());
    }

    /**
     * Used to fetch old data, incase we want to do comparisons between newly fetched data from network
     *
     * @return data from memory or disk
     */
    protected Observable<Parsed> getLocal(@NonNull final Id<Parsed> id) {
        return Observable.concat(
                memory(id),
                disk(id)
        ).first();
    }

    /**
     * Fetch data from disk and update memory after. If an error occurs, emit and empty observable
     * so that the concat call in {@link #get(Id)} moves on to {@link #network(Id)}
     *
     * @param id
     * @return
     */
    protected Observable<Parsed> disk(@NonNull final Id<Parsed> id) {
        return getDiskDAO().getData(id)
                .map(parser())
                .doOnNext(new Action1<Parsed>() {
                    @Override
                    public void call(Parsed parsed) {
//                        LOGGER.info("Getting  from disk and updating memory");
                        updateMemory(id, parsed);
                        Debug.stopMethodTracing();
                    }
                })
                .onErrorResumeNext(new OnErrorResumeWithEmpty<Parsed>());
    }

    /**
     * Will check to see if there exists an in flight observable and return it before
     * going to nerwork
     *
     * @return data from network and store it in memory and disk
     */
    protected Observable<Parsed> network(@NonNull final Id<Parsed> id) {
        return getDataFromNetworkAndSave(id);
    }

    /**
     * There should only be one network request in flight at any give time.
     * <p/>
     * Return cached request in the form of a Behavior Subject which will emit to its subscribers the
     * last value it gets. Subject/Observable is cached in a {@link ConcurrentMap} to maintain thread safety.
     *
     * @param id       resource identifier
     * @return observable that emits a {@link Parsed} value
     */
    protected Observable<Parsed> getDataFromNetworkAndSave(@NonNull final Id<Parsed> id) {
        try {
            return inFlightRequests.get(id, new Callable<Observable<Parsed>>() {
                @Override
                public Observable<Parsed> call() {
                    return getNetworkResponse(id).cache();
                }
            });
        } catch (ExecutionException e) {
            return Observable.empty();
        }
    }

    @NonNull
    protected Observable<Parsed> getNetworkResponse(@NonNull final Id<Parsed> id) {
        return getNetworkDAO().fetch(id)
                .map(new Func1<Raw, Parsed>() {
                    @Override
                    public Parsed call(Raw raw) {
                        LOGGER.info("Getting  from network updating memory and disk");
                        Parsed parsedData = parser().call(raw);
                        updateMemory(id, parsedData);
                        save(id).call(raw);
                        return parsedData;
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        inFlightRequests.invalidate(id);
                    }
                });


    }

    /**
     * Only update memory after disk has been successfully update
     *
     * @param id
     * @param data
     */
    protected void updateMemory(@NonNull final Id<Parsed> id, final Parsed data) {
        memory.put(id, data);
    }


    protected void clearMemory() {
        memory.invalidateAll();
    }

    /**
     * Clear memory by id
     *
     * @param id of data to clear
     */
    protected void clearMemory(@NonNull final Id<Parsed> id) {
        memory.invalidate(id);
    }

    /**
     * Default Cache TTL, can be overridden
     *
     * @return memory cache ttl
     */
    protected long getCacheTTL() {
        return TimeUnit.HOURS.toMillis(24);
    }

    /**
     * Default mem cache is 1, can be overridden otherwise
     *
     * @return memory cache size
     */
    protected int getCacheSize() {
        return 1;
    }

    /**
     * @return DiskDAO that stores and stores <Raw> data
     */
    protected abstract BaseDiskDAO<Raw, Parsed> getDiskDAO();

    /**
     *
     */
    protected abstract NetworkDAO<Raw, Parsed> getNetworkDAO();

    protected abstract Func1<Raw, Parsed> parser();

    /**
     * Save latestfeed string to disk and object to memory if disk save succeeds
     */
    protected Action1<Raw> save(@NonNull final Id<Parsed> id) {
        return new Action1<Raw>() {
            @Override
            public void call(Raw rawData) {
                getDiskDAO().store(id, rawData).subscribe(new NYTSubscriber<Record<Parsed>>(BaseStore.class) {
                    @Override
                    public void onNext(Record<Parsed> parsedRecord) {
                        //do nothing
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LOGGER.error("Failed to save parsed data of {} to disk. {}", id, throwable);
                    }
                });
            }
        };
    }
}

