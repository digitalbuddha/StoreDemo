package com.nytimes.storedemo.store.article;

import com.nytimes.storedemo.model.RedditData;
import com.nytimes.storedemo.rest.RedditApi;
import com.nytimes.storedemo.store.base.NetworkDAO;
import com.nytimes.storedemo.util.Id;

import javax.inject.Inject;

import rx.Observable;

public class RedditNetworkDao implements NetworkDAO<RedditData> {
    @Inject
    RedditApi api;


    @Inject
    public RedditNetworkDao() {
    }

    @Override
    //will eventually go to retrofit & okhttp cache
    public Observable<RedditData> fetch(Id<RedditData> id) {
        return api.aww();
    }
}
