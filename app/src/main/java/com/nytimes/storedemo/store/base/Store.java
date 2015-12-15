package com.nytimes.storedemo.store.base;

import android.support.annotation.NonNull;

import com.nytimes.storedemo.util.Id;
import com.nytimes.storedemo.util.OnErrorResumeWithEmpty;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Store to be used for loading an object different data sources
 *
 * @param <Raw>    data type before parsing
 * @param <Parsed> data type after parsing
 *                 <p>
 *                 Example usage:  {@link }
 *                 <p>
 *                 get = cached data if not stale
 *                 network=skip get and disk
 */
public abstract class Store<Raw, Parsed> {
    //    private static final Logger LOGGER = LoggerFactory.getLogger(Store.class);
    private final MemoryCache<Parsed> cache;
    protected Func1<Raw, Parsed> parser;
    protected DiskDAO<Raw, Parsed> diskDAO;
    protected final NetworkDAO<Raw, Parsed> networkDAO;


    public Store(Func1<Raw, Parsed> parser, DiskDAO<Raw, Parsed> diskDAO, NetworkDAO<Raw, Parsed> networkDAO) {
        cache = MemoryCache.create();
        this.parser = parser;
        this.diskDAO = diskDAO;
        this.networkDAO = networkDAO;
    }

    /**
     * @param id
     * @return an observable from the first data source that is available
     */
    public Observable<Parsed> get(@NonNull final Id<Parsed> id) {
//        LOGGER.info("Getting from Store");
        return Observable.concat(
                cache.get(id),
                disk(id),
                network(id)
        ).first();
    }


    /**
     * Used to fetch old data, incase we want to do comparisons between newly fetched data from network
     *
     * @return data from get or disk
     */
    protected Observable<Parsed> getLocal(@NonNull final Id<Parsed> id) {
        return Observable.concat(
                cache.get(id),
                disk(id)
        ).first();
    }

    /**
     * Fetch data from disk and update get after. If an error occurs, emit and empty observable
     * so that the concat call in {@link #get(Id)} moves on to {@link #network(Id)}
     *
     * @param id
     * @return
     */
    protected Observable<Parsed> disk(@NonNull final Id<Parsed> id) {
        return getDiskDAO().getData(id)
                .map(parser())
                .doOnNext(parsed -> {
//                        LOGGER.info("Getting  from disk and updating get");
                    cache.update(id, parsed);
                })
                .onErrorResumeNext(new OnErrorResumeWithEmpty<>());
    }

    /**
     * Will check to see if there exists an in flight observable and return it before
     * going to nerwork
     *
     * @return data from network and store it in get and disk
     */
    public Observable<Parsed> network(@NonNull final Id<Parsed> id) {
        return getDataFromNetworkAndSave(id);
    }

    /**
     * There should only be one network request in flight at any give time.
     * <p>
     * Return cached request in the form of a Behavior Subject which will emit to its subscribers the
     * last value it gets. Subject/Observable is cached in a {@link ConcurrentMap} to maintain thread safety.
     *
     * @param id resource identifier
     * @return observable that emits a {@link Parsed} value
     */
    protected Observable<Parsed> getDataFromNetworkAndSave(@NonNull final Id<Parsed> id) {
        try {
            return cache.inFlightRequests.get(id, () -> getNetworkResponse(id).cache());
        } catch (ExecutionException e) {
            return Observable.empty();
        }
    }

    @NonNull
    protected Observable<Parsed> getNetworkResponse(@NonNull final Id<Parsed> id) {
        return getNetworkDAO().fetch(id)
                .map(raw -> {//LOGGER.info("Getting  from network updating get and disk");
                    //parse before save to disk to make sure no parsing errors
                    Parsed parsedData = parser().call(raw);
                    save(id).call(raw);
                    cache.update(id, parsedData);
                    return parsedData;
                })
                .doOnError(throwable -> cache.inFlightRequests.invalidate(id));


    }


    protected void updateMemory(@NonNull final Id<Parsed> id, final Parsed data) {
        cache.update(id, data);
    }


    protected void clearMemory() {
        cache.clearMemory();
    }

    /**
     * Clear get by id
     *
     * @param id of data to clear
     */
    protected void clearMemory(@NonNull final Id<Parsed> id) {
        cache.clearMemory(id);
    }


    protected DiskDAO<Raw, Parsed> getDiskDAO() {
        return diskDAO;
    }

    protected NetworkDAO<Raw, Parsed> getNetworkDAO() {
        return networkDAO;
    }

    protected Func1<Raw, Parsed> parser() {
        return parser;
    }

    /**
     * Save raw data to disk
     * currently disk save is done asynchronously after get has been updated
     */
    protected Action1<Raw> save(@NonNull final Id<Parsed> id) {
        return rawData -> {
            getDiskDAO().store(id, rawData).subscribe();
        };
    }
}

