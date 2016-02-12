package com.digitalbuddha.daodemo.base;

import android.support.annotation.NonNull;

import com.digitalbuddha.daodemo.util.Id;

import rx.Observable;

/**
 * RxStore to be used for loading an object different data sources
 *
 * @param <T> data type after parsing
 *                 <p>
 *                 get = cached data if not stale otherwise network, updates caches
 *                 network=skip memory and disk cache, still updates caches
 */
public abstract class RxStore<T> implements Store<T> {
    private final RxCache<T> cache;


    public RxStore() {
        cache = RxCache.create();
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
        return fetch(id,"fresh and clean")
                .doOnNext(data -> cache.update(id, data));
    }

    protected Observable<T> getNetworkResponse(@NonNull final Id<T> id) {
        return fetch(id,null)
                .doOnNext(data -> cache.update(id, data));
    }

   public abstract Observable<T> fetch(Id<T> id, String forceNetwork);

    public void clearMemory() {
        cache.clearMemory();
    }

    public void clearMemory(@NonNull final Id<T> id) {
        cache.clearMemory(id);
    }

}

