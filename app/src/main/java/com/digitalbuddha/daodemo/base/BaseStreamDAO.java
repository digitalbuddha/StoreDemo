package com.digitalbuddha.daodemo.base;

import android.support.annotation.NonNull;

import com.digitalbuddha.daodemo.util.Id;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Base class for handling Raw and T data subscription logic
 *
 * @param <T> data type after parsing
 *
 */
public abstract class BaseStreamDAO<T> extends BaseDAO<T> {

    private final BehaviorSubject<T> subject;

    public BaseStreamDAO(DAOLoaderInterface<T> DAOLoader) {
        super(DAOLoader);
        subject = BehaviorSubject.create();
    }

    /**
     * To be exposed to clients for subscribing to endless data streams.
     * Any client that subscribers to the steam will receive updates for all data of
     * type {@link T}
     */
    public  Observable<T> stream(){
        return subject.asObservable();
    }

    @Override
    protected Observable<T> fetch(@NonNull Id<T> id) {
        return super.fetch(id)
                .doOnNext(this::notify);
    }

    /**
     * Notify the subscribers of the subject of data change
     *
     * @param data to be emitted to subscribers of Subject
     */
    protected void notify(T data) {
        subject.onNext(data);
    }
}
