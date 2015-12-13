package com.nytimes.storedemo.store.base;

import android.support.annotation.NonNull;

import com.nytimes.storedemo.util.Id;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Base class for handling Raw and Parsed data subscription logic
 *
 * @param <Raw>    data type before parsing
 * @param <Parsed> data type after parsing
 *
 */
public abstract class BaseSubscriptionStore<Raw, Parsed> extends BaseStore<Raw, Parsed> {

    private final BehaviorSubject<Parsed> subject;

    public BaseSubscriptionStore() {
        super();
        subject = BehaviorSubject.create();
    }

    /**
     * To be exposed to clients for subscribing to endless data streams.
     * Any client that subscribers to the steam will receive updates for all data of
     * type {@link Parsed}
     */
    public  Observable<Parsed> stream(){
        return subject.asObservable();
    }

    @Override
    protected Observable<Parsed> getNetworkResponse(@NonNull Id<Parsed> id) {
        return super.getNetworkResponse(id)
                .doOnNext(data -> notifySubscribers(data));
    }

    /**
     * Notify the subscribers of the subject of data change
     *
     * @param data to be emitted to subscribers of Subject
     */
    protected void notifySubscribers(Parsed data) {
        subject.onNext(data);
    }
}
