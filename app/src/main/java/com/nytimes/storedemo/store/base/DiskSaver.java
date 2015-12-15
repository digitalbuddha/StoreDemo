package com.nytimes.storedemo.store.base;

import com.nytimes.storedemo.util.Id;
import com.nytimes.storedemo.util.Record;

import rx.Observable;

/**
 * Interface for fetching data from disk
 *
 * @param <Raw>    data type before parsing
 * @param <Parsed> data type after parsing
 */
public interface DiskSaver<Raw, Parsed> {
    /**
     * @param id to use to store data to disk
     * @param raw  raw string to be stored
     */
    Observable<Record<Parsed>> store(Id<Parsed> id, Raw raw);
}
