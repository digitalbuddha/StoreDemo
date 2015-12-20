package com.nytimes.storedemo.store.reddit;

import com.nytimes.storedemo.model.RedditData;
import com.nytimes.storedemo.rest.APIS;
import com.nytimes.storedemo.rest.RedditApi;
import com.nytimes.storedemo.store.base.NetworkDAO;
import com.nytimes.storedemo.util.Id;

import javax.inject.Inject;

import rx.Observable;

public class RedditNetworkDao implements NetworkDAO<RedditData> {
    @Inject
    APIS<RedditApi> api;


    @Inject
    public RedditNetworkDao() {
    }

    @Override
    public Observable<RedditData> fetch(Id<RedditData> id) {
        return api.cached().aww();
    }

    @Override
    public Observable<RedditData> fresh(Id<RedditData> id) {
        return api.fresh().aww();
    }
}
