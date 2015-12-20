package com.digitalbuddha.storedemo.rest;

/**
 * wrapper class for holding both a cached and non cached version of api
 * api differences come from request interceptor
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
