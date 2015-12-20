package com.digitalbuddha.storedemo.store.reddit;

import com.digitalbuddha.storedemo.model.RedditData;
import com.digitalbuddha.storedemo.rest.RedditApi;
import com.digitalbuddha.storedemo.util.Id;

import javax.inject.Inject;

import rx.Observable;

public class RedditNetworkDao extends AbstractNetworkDao<RedditData> {
    @Inject
    RedditApi api;

    @Inject
    public RedditNetworkDao() {
    }

    @Override
    public Observable<RedditData> fetch(Id<RedditData> id) {
        return api.aww(id.getKey());
    }

    @Override
    public Observable<RedditData> fresh(Id<RedditData> id) {
        removeFromCache(id);
        return fetch(id);
    }

    //url is needed to clear cache
    @Override
    public String getUrl() {
        return "http://reddit.com/r/aww/new/.json?limit=";
    }
}
