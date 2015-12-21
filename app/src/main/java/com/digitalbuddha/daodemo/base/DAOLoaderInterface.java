package com.digitalbuddha.daodemo.base;

import com.digitalbuddha.daodemo.util.Request;

import rx.Observable;

/**
 * Interface for fetching data from the network
 *
 */
public interface DAOLoaderInterface<T> {
    Observable<T> fetch( Request<T> request);
    Observable<T> fresh(Request<T> request);
}
