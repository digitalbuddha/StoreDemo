package com.nytimes.storedemo.store.base;

import android.support.annotation.NonNull;

import com.nytimes.storedemo.util.Id;

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
        return cache.get(id, getNetworkResponse(id));
    }



    /**
     * Will check to see if there exists an in flight observable and return it before
     * going to nerwork
     *
     * @return data from fresh and store it in get and disk
     */
    @Override
    public Observable<T> fresh(@NonNull final Id<T> id) {
        return freshNetworkResponse(id);
    }



    protected Observable<T> getNetworkResponse(@NonNull final Id<T> id) {
        return getNetworkDAO().fetch(id)
                .doOnNext(data -> {//LOGGER.info("Getting  from fresh updating get and disk");
                    //parse before save to disk to make sure no parsing errors
                    cache.update(id, data);
                });
    }

    protected Observable<T> freshNetworkResponse(@NonNull final Id<T> id) {
        return getNetworkDAO().fresh(id)
                .doOnNext(data -> {//LOGGER.info("Getting  from fresh updating get and disk");
                    //parse before save to disk to make sure no parsing errors
                    cache.update(id, data);
                });
    }


    public void updateMemory(@NonNull final Id<T> id, final T data) {
        cache.update(id, data);
    }


    public void clearMemory() {
        cache.clearMemory();
    }

    /**
     * Clear get by id
     *
     * @param id of data to clear
     */
    public void clearMemory(@NonNull final Id<T> id) {
        cache.clearMemory(id);
    }

    protected NetworkDAO<T> getNetworkDAO() {
        return networkDAO;
    }


}

