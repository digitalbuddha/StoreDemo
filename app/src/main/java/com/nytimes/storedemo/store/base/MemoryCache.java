package com.nytimes.storedemo.store.base;

import android.support.annotation.NonNull;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.nytimes.storedemo.util.Id;
import com.nytimes.storedemo.util.OnErrorResumeWithEmpty;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public class MemoryCache<Parsed> {//MemoryCache cache
    //in memory cache of data
    final Cache<Id<Parsed>, Parsed> memory;
    //Serves to prevent network request duplications
    final Cache<Id<Parsed>, Observable<Parsed>> inFlightRequests;

    private MemoryCache() {
        memory = CacheBuilder.newBuilder()
                .maximumSize(getCacheSize())
                .expireAfterAccess(getCacheTTL(), TimeUnit.MILLISECONDS)
                .build();
        inFlightRequests = CacheBuilder.newBuilder()
                .expireAfterWrite(TimeUnit.MINUTES.toMillis(1), TimeUnit.MILLISECONDS)
                .build();
    }

    public static <Parsed> MemoryCache<Parsed> create() {
        return new MemoryCache<>();
    }

    /**
     * @return data from get
     */
    protected Observable<Parsed> get(@NonNull final Id<Parsed> id) {
        return Observable
                .defer(() -> {
//                        LOGGER.info("Getting  from get");
                    Parsed data = memory.getIfPresent(id);
                    return data == null ? Observable.<Parsed>empty() : Observable.just(data);
                })
                .onErrorResumeNext(new OnErrorResumeWithEmpty<Parsed>());
    }

    protected void update(@NonNull final Id<Parsed> id, final Parsed data) {
        memory.put(id, data);
    }

    protected void clearMemory() {
        memory.invalidateAll();
    }

    /**
     * Clear get by id
     *
     * @param id of data to clear
     */
    protected void clearMemory(@NonNull final Id<Parsed> id) {
        memory.invalidate(id);
    }

    /**
     * Default Cache TTL, can be overridden
     *
     * @return get cache ttl
     */
    protected long getCacheTTL() {
        return TimeUnit.HOURS.toMillis(24);
    }

    /**
     * Default mem cache is 1, can be overridden otherwise
     *
     * @return get cache size
     */
    protected int getCacheSize() {
        return 1;
    }
}