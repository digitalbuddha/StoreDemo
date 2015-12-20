package com.nytimes.storedemo.rest;

import com.nytimes.storedemo.model.RedditData;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface RedditApi {
    @GET("/r/aww/.json")
    Observable<RedditData> aww(@Query("limit") String limit);
}