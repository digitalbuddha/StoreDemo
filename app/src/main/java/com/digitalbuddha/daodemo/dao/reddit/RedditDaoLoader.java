package com.digitalbuddha.daodemo.dao.reddit;

import com.digitalbuddha.daodemo.dao.base.BaseDAOLoader;
import com.digitalbuddha.daodemo.model.RedditData;
import com.digitalbuddha.daodemo.rest.RedditApi;
import com.digitalbuddha.daodemo.util.Id;

import javax.inject.Inject;

import rx.Observable;

public class RedditDaoLoader extends BaseDAOLoader<RedditData> {
    @Inject
    RedditApi api;

    @Inject
    public RedditDaoLoader() {
    }

    @Override
    public Observable<RedditData> fetch(Id<RedditData> id) {
        return api.aww(id.getKey());
    }



    //url is needed to clear cache
    @Override
    public String getUrl() {
        return "http://reddit.com/r/aww/new/.json?limit=";
    }
}
