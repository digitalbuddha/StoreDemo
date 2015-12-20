package com.nytimes.storedemo.store.base;

import com.nytimes.storedemo.util.Id;

import rx.Observable;

/**
 * Interface for fetching data from the network
 *
 */
public interface NetworkDAO<T> {
    Observable<T> fetch( Id<T> id);
    Observable<T> fresh(Id<T> id);
}
