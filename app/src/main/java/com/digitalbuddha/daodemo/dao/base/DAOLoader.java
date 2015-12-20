package com.digitalbuddha.daodemo.dao.base;

import com.digitalbuddha.daodemo.util.Id;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.Iterator;

import javax.inject.Inject;

import rx.Observable;

public abstract class DAOLoader<T> implements DAOLoaderInterface<T> {
    @Inject
    OkHttpClient client;

    protected void removeFromCache(Id<T> id) {
        try {
            Iterator<String> urls = client.getCache().urls();
            while (urls.hasNext()) {
                String cachedKey = urls.next();
                if (cachedKey.equals(getUrl() + id.getKey())) {
                    urls.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Observable<T> fresh(Id<T> id) {
        removeFromCache(id);
        return fetch(id);
    }
}
