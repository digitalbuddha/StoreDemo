package com.nytimes.storedemo.store.base;

import com.nytimes.storedemo.store.util.Id;
import com.nytimes.storedemo.store.util.Record;

import rx.Observable;

/**
 * Interface for fetching data from disk
 *
 * @param <Raw>    data type before parsing
 * @param <Parsed> data type after parsing
 */
public interface DiskDAO<Raw, Parsed> {

    /**
     * @param id to use to get data from disk
     * @return An observable which emits {@link Parsed} data from disk
     */
    Observable<Raw> getData(final Id<Parsed> id);

    /**
     * @param id to use to store data to disk
     * @param raw  raw string to be stored
     */
    Observable<Record<Parsed>> store(Id<Parsed> id, Raw raw);
}
