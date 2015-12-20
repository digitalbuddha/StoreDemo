package com.nytimes.storedemo.rest;

/**
 * Created by 206847 on 12/19/15.
 */
public class APIS<T> {


    private final T cacheApi;
    private final T freshApi;

    public APIS(T cacheApi, T freshApi) {
        this.cacheApi = cacheApi;
        this.freshApi = freshApi;
    }

    public T fresh() {
        return cacheApi;
    }

    public T cached() {
        return freshApi;
    }
}
