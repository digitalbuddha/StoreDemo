package com.digitalbuddha.daodemo.base;

import com.digitalbuddha.daodemo.util.Id;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Inject;

import rx.Observable;

public abstract class BaseDAOLoader<T> implements DAOLoaderInterface<T> {
    @Inject
    OkHttpClient client;

    public Observable<T> fresh(Id<T> id) {
        return fetch(id,"freshAndClean!!");
    }

    public Observable<T> fetch(Id<T> id) {
        return fetch(id,null);
    }

}
