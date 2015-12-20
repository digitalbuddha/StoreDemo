package com.digitalbuddha.storedemo.store.base;

import android.support.annotation.NonNull;

import com.digitalbuddha.storedemo.util.Id;

import rx.Observable;

/**
 * Store to be used for loading an object different data sources
 *
 * @param <T> data type after parsing
 *                 <p>
 *                 get = cached data if not stale otherwise network, updates caches
 *                 network=skip memory and disk cache, still updates caches
 */
public abstract class Store<T> implements RxStore<T> {
    private final MemoryCache<T> cache;
    protected final NetworkDAO<T> networkDAO;


    public Store(NetworkDAO<T> networkDAO) {
        cache = MemoryCache.create();
        this.networkDAO = networkDAO;
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
        return networkDAO.fresh(id)
                .doOnNext(data -> cache.update(id, data));
    }

    protected Observable<T> getNetworkResponse(@NonNull final Id<T> id) {
        return networkDAO.fetch(id)
                .doOnNext(data -> cache.update(id, data));
    }

    public void clearMemory() {
        cache.clearMemory();
    }

    public void clearMemory(@NonNull final Id<T> id) {
        cache.clearMemory(id);
    }

}

