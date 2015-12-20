package com.digitalbuddha.daodemo.reddit.data;

import com.digitalbuddha.daodemo.reddit.data.model.RedditData;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface RedditApi {
    @GET("r/aww/new/.json")
    Observable<RedditData> aww(@Query("limit") String limit);
}