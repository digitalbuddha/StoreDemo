package com.digitalbuddha.daodemo.dao.base;

import android.support.annotation.NonNull;

import com.digitalbuddha.daodemo.util.Id;

import rx.Observable;

/**
 * DAO to be used for loading an object different data sources
 *
 * @param <T> data type after parsing
 *                 <p>
 *                 get = cached data if not stale otherwise network, updates caches
 *                 network=skip memory and disk cache, still updates caches
 */
public abstract class DAO<T> implements RxDAO<T> {
    private final DAOCache<T> cache;
    protected final DAOLoaderInterface<T> DAOLoader;


    public DAO(DAOLoaderInterface<T> DAOLoader) {
        cache = DAOCache.create();
        this.DAOLoader = DAOLoader;
    }

    /**
     * @return an observable from the first data source
     * memory/disk/network that is available and not stale
     */
    @Override
    public Observable<T> get(@NonNull final Id<T> id)  {
        return cache.get(id, getNetworkResponse(id));
    }

    /**
     * @return force network and update disk/memory
     */
    @Override
    public Observable<T> fresh(@NonNull final Id<T> id) {
        return DAOLoader.fresh(id)
                .doOnNext(data -> cache.update(id, data));
    }

    protected Observable<T> getNetworkResponse(@NonNull final Id<T> id) {
        return DAOLoader.fetch(id)
                .doOnNext(data -> cache.update(id, data));
    }

    public void clearMemory() {
        cache.clearMemory();
    }

    public void clearMemory(@NonNull final Id<T> id) {
        cache.clearMemory(id);
    }

}

