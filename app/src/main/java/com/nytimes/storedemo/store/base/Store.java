package com.nytimes.storedemo.store.base;

import android.support.annotation.NonNull;

import com.nytimes.storedemo.util.Id;

import java.util.concurrent.ConcurrentMap;

import rx.Observable;

/**
 * Store to be used for loading an object different data sources
 *
 * @param <T> data type after parsing
 *                 <p>
 *                 Example usage:  {@link }
 *                 <p>
 *                 get = cached data if not stale
 *                 network=skip get and disk
 */
public abstract class Store<T> implements RxStore<T> {
    //    private static final Logger LOGGER = LoggerFactory.getLogger(Store.class);
    private final MemoryCache<T> cache;
    protected final NetworkDAO<T> networkDAO;


    public Store(NetworkDAO<T> networkDAO) {
        cache = MemoryCache.create();
        this.networkDAO = networkDAO;
    }

    /**
     * @param id
     * @return an observable from the first data source that is available
     */
    @Override
    public Observable<T> get(@NonNull final Id<T> id)  {
//        LOGGER.info("Getting from Store");
        return cache.get(id, fresh(id));
    }



    /**
     * Will check to see if there exists an in flight observable and return it before
     * going to nerwork
     *
     * @return data from fresh and store it in get and disk
     */
    @Override
    public Observable<T> fresh(@NonNull final Id<T> id) {
        return getDataFromNetworkAndSave(id);
    }

    /**
     * There should only be one fresh request in flight at any give time.
     * <p>
     * Return cached request in the form of a Behavior Subject which will emit to its subscribers the
     * last value it gets. Subject/Observable is cached in a {@link ConcurrentMap} to maintain thread safety.
     *
     * @param id resource identifier
     * @return observable that emits a {@link T} value
     */
    protected Observable<T> getDataFromNetworkAndSave(@NonNull final Id<T> id) {
            return  getNetworkResponse(id);
    }

    protected Observable<T> getNetworkResponse(@NonNull final Id<T> id) {
        return getNetworkDAO().fetch(id)
                .doOnNext(data -> {//LOGGER.info("Getting  from fresh updating get and disk");
                    //parse before save to disk to make sure no parsing errors
                    cache.update(id, data);
                });
    }


    protected void updateMemory(@NonNull final Id<T> id, final T data) {
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
    protected void clearMemory(@NonNull final Id<T> id) {
        cache.clearMemory(id);
    }

    protected NetworkDAO<T> getNetworkDAO() {
        return networkDAO;
    }




}

