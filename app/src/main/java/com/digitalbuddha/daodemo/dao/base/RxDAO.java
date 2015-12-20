package com.digitalbuddha.daodemo.dao.base;

import android.support.annotation.NonNull;

import com.digitalbuddha.daodemo.util.Id;

import rx.Observable;

/**
 * Created by 206847 on 12/19/15.
 */
public interface RxDAO<T> {
    Observable<T> get(@NonNull Id<T> id);

    Observable<T> fresh(@NonNull Id<T> id);
}
