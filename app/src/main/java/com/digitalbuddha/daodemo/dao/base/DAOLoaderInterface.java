package com.digitalbuddha.daodemo.dao.base;

import com.digitalbuddha.daodemo.util.Id;

import rx.Observable;

/**
 * Interface for fetching data from the network
 *
 */
public interface DAOLoaderInterface<T> {
    Observable<T> fetch( Id<T> id);
    Observable<T> fresh(Id<T> id);
    String getUrl();
}
