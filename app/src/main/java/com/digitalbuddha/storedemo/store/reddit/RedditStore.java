package com.digitalbuddha.storedemo.store.reddit;


import com.digitalbuddha.storedemo.model.RedditData;
import com.digitalbuddha.storedemo.store.base.Store;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class RedditStore extends Store<RedditData> {

    @Inject
    public RedditStore(RedditNetworkDao networkDAO) {
        super(networkDAO);
    }
}
