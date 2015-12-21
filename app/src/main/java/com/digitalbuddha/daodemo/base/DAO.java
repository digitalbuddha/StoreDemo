package com.digitalbuddha.daodemo.base;

import android.support.annotation.NonNull;

import com.digitalbuddha.daodemo.util.Request;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.Iterator;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * DAO to be used for loading an object different data sources
 *
 * @param <T> data type after parsing
 *            <p>
 *            get = cached data if not stale otherwise network, updates caches
 *            network=skip memory and disk cache, still updates caches
 */
@Singleton
public class DAO<T>{
    private final DAOCache<T> cache;
    private final BehaviorSubject<T> subject;
    @Inject
    OkHttpClient client;

    public DAO() {
        cache = DAOCache.create();
        subject = BehaviorSubject.create();
    }

    public Observable<T> get(final Request<T> request) {
        return cache.get(request).doOnNext(data -> cache.update(request, data));
    }

    public Observable<T> fresh(final Request<T> request) {
        removeFromCache(request);
        Observable<T> response = (Observable<T>) request.getResponse();
        return response.doOnNext(data -> cache.update(request, data)).doOnNext
                (this::notifySubscribers);

    }

    public void clearMemory() {
        cache.clearMemory();
    }

    public void clearMemory(@NonNull final Request<T> request) {
        cache.clearMemory(request);
    }

    public  Observable<T> stream(){
        return subject.asObservable();
    }

    protected void notifySubscribers(T data) {
        subject.onNext(data);
    }
    protected void removeFromCache(Request<T> request) {
        try {
            Iterator<String> urls = client.getCache().urls();
            while (urls.hasNext()) {
                String cachedKey = urls.next();
                if (cachedKey.equals(request.getKey())) {
                    urls.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

