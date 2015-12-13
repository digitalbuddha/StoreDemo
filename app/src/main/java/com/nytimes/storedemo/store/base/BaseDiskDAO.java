package com.nytimes.storedemo.store.base;

import com.nytimes.android.feed.content.fn.IsRecordExpired;
import com.nytimes.android.io.Id;
import com.nytimes.android.io.persistence.PersistenceManager;
import com.nytimes.android.io.persistence.Record;
import com.nytimes.android.store.feed.rx.DiskReadErrorAction;
import com.nytimes.storedemo.store.util.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.Lazy;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Abstract class implements base logic for reading and storing data from/to disk
 *
 * @param <Raw>    data pre parse
 * @param <Parsed> data post parse
 */
public abstract class BaseDiskDAO<Raw, Parsed> implements DiskDAO<Raw, Parsed> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseStore.class);

    @Inject
    Lazy<PersistenceManager> persistenceManager;

    @Override
    public Observable<Raw> getData(final Id<Parsed> id) {
        LOGGER.info("Getting data of type {} and with key {} from disk", id.getType(), id.getKey());
        return readRecordState(id, getDiskExpiration())
                .flatMap(new Func1<IsRecordExpired.RecordState, Observable<Raw>>() {
                    @Override
                    public Observable<Raw> call(IsRecordExpired.RecordState state) {
                        if (state == IsRecordExpired.RecordState.MISSING) {
                            //Nothing found, return empty observable that completes
                            return Observable.empty();
                        } else if (state == IsRecordExpired.RecordState.STALE) {
                            //TODO need a better solution to refresh in background instead of doOnNext
                            return read(id).doOnNext(new Action1<Raw>() {
                                @Override
                                public void call(Raw raw) {
                                    actionIfDiskStale(raw);
                                }
                            });
                        } else {
                            // RecordState.FRESH
                            return read(id);
                        }
                    }
                });
    }

    /**
     * Read data from disk
     *
     * @param id
     * @return
     */
    protected abstract Observable<Raw> read(final Id<Parsed> id);

    /**
     * Get Reader for Data
     *
     * @param id
     * @return
     */
    protected Observable<Reader> readAsReader(final Id<Parsed> id) {
        LOGGER.info("reading reader of {}", id);
        return getPersistenceManager().getReader(id)
                .doOnError(new DiskReadErrorAction(id));
    }

    protected Observable<String> readAsString(final Id<Parsed> id) {
        LOGGER.info("reading string of {}", id);
        return getPersistenceManager().readString(id)
                .doOnError(new DiskReadErrorAction(id));
    }

    protected Observable<Record<Parsed>> storeAsString(Id<Parsed> id, String raw) {
        return getPersistenceManager().storeString(id, raw);
    }

    private Observable<IsRecordExpired.RecordState> readRecordState(Id<Parsed> id,
                                                                    Func1<Record<Parsed>,
                                                                            IsRecordExpired.RecordState> freshTest) {
        return getPersistenceManager().getRecord(id)
                .map(freshTest)
                .onErrorResumeNext(Observable.just(IsRecordExpired.RecordState.MISSING));
    }

    /**
     * Remove data from disk
     *
     * @param id of data
     * @return
     */
    protected Observable<Record<Parsed>> clear(final Id<Parsed> id) {
        return getPersistenceManager().delete(id)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LOGGER.info("failed to delete {} from disk", id);
                    }
                });
    }

    protected PersistenceManager getPersistenceManager() {
        return persistenceManager.get();
    }

    /**
     * @return Action to perform when stale data is encountered
     */
    protected void actionIfDiskStale(Raw raw) {
        //Do Nothing, base implementation so that subclasses don't need to implement
    }

    protected Func1<Record<Parsed>, IsRecordExpired.RecordState> getDiskExpiration() {
        return new IsRecordExpired<>(TimeUnit.HOURS.toMillis(24));
    }
}
