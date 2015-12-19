package com.nytimes.storedemo.rest;

import com.nytimes.storedemo.model.RedditData;

import retrofit.http.GET;
import rx.Observable;

public interface RedditApi {
    @GET("/r/aww/.json?limit=20")
    Observable<RedditData> aww();
}