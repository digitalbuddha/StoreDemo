package com.nytimes.storedemo.store.base;

import com.nytimes.storedemo.util.Id;

import rx.Observable;

/**
 * Interface for fetching data from disk
 *
 * @param <Raw>    data type before parsing
 * @param <Parsed> data type after parsing
 */
public interface DiskLoader<Raw, Parsed> {

    /**
     * @param id to use to get data from disk
     * @return An observable which emits {@link Parsed} data from disk
     */
    Observable<Raw> getData(final Id<Parsed> id);
}
