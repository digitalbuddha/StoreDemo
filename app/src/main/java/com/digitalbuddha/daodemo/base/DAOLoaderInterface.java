package com.digitalbuddha.daodemo.base;

import com.digitalbuddha.daodemo.util.Id;

import rx.Observable;

/**
 * Interface for fetching data from the network
 *
 */
public interface DAOLoaderInterface<T> {
    Observable<T> fetch( Id<T> id);
    String getCacheKey(Id<T> id);
    void removeFromCache(Id<T> id);
}
