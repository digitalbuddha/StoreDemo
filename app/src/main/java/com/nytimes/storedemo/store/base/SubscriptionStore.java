package com.nytimes.storedemo.store.base;

import android.support.annotation.NonNull;

import com.nytimes.storedemo.util.Id;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Base class for handling Raw and Parsed data subscription logic
 *
 * @param <Parsed> data type after parsing
 *
 */
public abstract class SubscriptionStore< Parsed> extends Store< Parsed> {

    private final BehaviorSubject<Parsed> subject;

    public SubscriptionStore( NetworkDAO<Parsed> networkDAO) {
        super(networkDAO);
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
