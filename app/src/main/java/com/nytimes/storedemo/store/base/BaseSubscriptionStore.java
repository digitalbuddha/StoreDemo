package com.nytimes.storedemo.store.base;

import android.support.annotation.NonNull;

import com.nytimes.android.io.Id;
import com.nytimes.android.io.network.Priority;
import com.nytimes.storedemo.store.util.Id;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

/**
 * Base class for handling Raw and Parsed data subscription logic
 *
 * @param <Raw>    data type before parsing
 * @param <Parsed> data type after parsing
 */
public abstract class BaseSubscriptionStore<Raw, Parsed> extends BaseStore<Raw, Parsed> {

    private final Subject<Parsed, Parsed> subject;

    public BaseSubscriptionStore() {
        super();
        subject = BehaviorSubject.create();
    }

    /**
     * To be exposed to clients for subscribing to endless data streams.
     * Any client that subscribers to the steam will receive updates for all data of
     * type {@link Parsed}
     */
    protected abstract Observable<Parsed> stream();

    @Override
    protected Observable<Parsed> getNetworkResponse(@NonNull Id<Parsed> id) {
        return super.getNetworkResponse(id)
                .doOnNext(new Action1<Parsed>() {
                    @Override
                    public void call(Parsed data) {
                        notifySubscribers(data);
                    }
                });
    }

    /**
     * Get data stream for Subjects with the argument id
     *
     * @return
     */
    protected Observable<Parsed> getStream(Id<Parsed> id) {

        Observable<Parsed> stream = subject.asObservable();

        //If nothing was emitted through the subject yet, start stream with get() value
        if (!subject.hasValue()) {
            return stream.startWith(get(id));
        }

        return stream;
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
