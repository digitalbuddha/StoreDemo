package com.nytimes.storedemo.rest;

import com.nytimes.storedemo.model.RedditData;

import retrofit.http.GET;

public interface RedditApi {
    @GET("/r/aww/.json?limit=20")
    RedditData aww();
}