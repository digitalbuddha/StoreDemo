package com.digitalbuddha.storedemo.store.base;

import com.digitalbuddha.storedemo.util.Id;

import rx.Observable;

/**
 * Interface for fetching data from the network
 *
 */
public interface NetworkDAO<T> {
    Observable<T> fetch( Id<T> id);
    Observable<T> fresh(Id<T> id);
    String getUrl();
}
