package com.nytimes.storedemo.store.base;

import com.nytimes.storedemo.util.Id;

import rx.Observable;

/**
 * Interface for fetching data from the network
 *
 * @param <Raw>    data type before parsing
 * @param <Parsed> data type after parsing
 */
public interface NetworkDAO<Raw, Parsed> {

    /**
     * @param id       Id is temporarily being used as the network request param, can be changed later
     * @return Observable that emits {@link Raw} data
     */
    Observable<Raw> fetch( Id<Parsed> id);
}
