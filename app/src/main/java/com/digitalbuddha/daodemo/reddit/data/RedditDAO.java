package com.digitalbuddha.daodemo.reddit.data;


import com.digitalbuddha.daodemo.base.BaseDAO;
import com.digitalbuddha.daodemo.reddit.data.model.RedditData;
import com.digitalbuddha.daodemo.util.Id;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;


@Singleton
public class RedditDAO extends BaseDAO<RedditData> {
    @Inject RedditApi api;

    @Inject
    public RedditDAO() {
        super();
    }

    public Observable<RedditData> fetch(Id<RedditData> id, String forceNetwork) {
        return api.aww(id.getKey(),forceNetwork);
    }
}
