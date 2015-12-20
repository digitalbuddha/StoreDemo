package com.digitalbuddha.storedemo.rest;

import com.digitalbuddha.storedemo.model.RedditData;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface RedditApi {
    @GET("r/aww/new/.json")
    Observable<RedditData> aww(@Query("limit") String limit);
}