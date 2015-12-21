package com.digitalbuddha.daodemo.base;

import android.support.annotation.NonNull;

import com.digitalbuddha.daodemo.util.Request;

import rx.Observable;

/**
 * Created by 206847 on 12/19/15.
 */
public interface RxDAO<T> {
    Observable<T> get(@NonNull Request<T> request);

    Observable<T> fresh(@NonNull Request<T> request);
}
