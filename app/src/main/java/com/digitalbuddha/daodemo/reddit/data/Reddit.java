package com.digitalbuddha.daodemo.reddit.data;

import com.digitalbuddha.daodemo.base.DAOResponse;
import com.digitalbuddha.daodemo.reddit.data.model.RedditData;

import javax.inject.Inject;

import retrofit.http.Query;
import rx.Observable;

public class Reddit extends DAOResponse<RedditData> implements RedditApi{
    //url is needed to clear cache
    public static String getUrl() {
        return "http://reddit.com/r/aww/new/.json?limit=";
    }

    @Inject
    RedditApi api;



    @Inject
    RedditDAO dao;

    @Inject
    public Reddit() {
    }


    @Override
    public Observable<RedditData> aww(@Query("limit") String limit) {
        return api.aww(limit);
    }

    public RedditDAO run() {
        return dao;
    }
}
