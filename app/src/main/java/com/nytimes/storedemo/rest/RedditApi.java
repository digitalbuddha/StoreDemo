package com.nytimes.storedemo.rest;

import retrofit.http.GET;

interface RedditApi {
    @GET("/r/aww/.json?limit=20")
    RedditApi aww();
}