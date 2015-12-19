package com.nytimes.storedemo.rest;

import com.nytimes.storedemo.model.RedditData;

import retrofit.http.GET;

interface RedditApi {
    @GET("/r/aww/.json?limit=20")
    RedditData aww();
}