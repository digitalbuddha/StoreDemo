package com.digitalbuddha.storedemo.store.base;

import android.support.annotation.NonNull;

import com.digitalbuddha.storedemo.util.Id;

import rx.Observable;

/**
 * Created by 206847 on 12/19/15.
 */
public interface RxStore<T> {
    Observable<T> get(@NonNull Id<T> id);

    Observable<T> fresh(@NonNull Id<T> id);
}
