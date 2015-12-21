package com.digitalbuddha.daodemo.base;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Inject;

public abstract class BaseDAOLoader<T> {
    @Inject
    OkHttpClient client;

//    protected void removeFromCache(Request<T> id) {
//        try {
//            Iterator<String> urls = client.getCache().urls();
//            while (urls.hasNext()) {
//                String cachedKey = urls.next();
//                if (cachedKey.equals(getUrl() + id.getKey())) {
//                    urls.remove();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Observable<T> fresh(Request<T> id) {
//        removeFromCache(id);
//        return fetch(id);
//    }
}
