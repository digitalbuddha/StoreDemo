package com.digitalbuddha.daodemo.reddit.data;


import com.digitalbuddha.daodemo.base.BaseStore;
import com.digitalbuddha.daodemo.reddit.data.model.RedditData;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;


@Singleton
public class RedditStore extends BaseStore<RedditData,String> {
    @Inject RedditApi api;

    @Inject
    public RedditStore() {
        super();
    }

    public Observable<RedditData> fetch(String limit, String forceNetwork) {
        return api.aww(limit,forceNetwork);
    }
}
