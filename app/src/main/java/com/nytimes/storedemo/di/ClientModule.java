package com.nytimes.storedemo.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nytimes.storedemo.model.GsonAdaptersModel;
import com.nytimes.storedemo.rest.APIS;
import com.nytimes.storedemo.rest.RedditApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module
public class ClientModule {
    @Singleton
    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(new GsonAdaptersModel());
        return gsonBuilder.create();
    }

    @Singleton
    @Provides
    APIS<RedditApi> provideRedditApi(RestAdapter.Builder builder) {
        RedditApi cacheApi = builder.setEndpoint("http://reddit.com")
                .build().create(RedditApi.class);

        RedditApi freshApi = builder.setEndpoint("http://reddit.com")
                .setRequestInterceptor(request -> {
                })//clear out the inteceptor
                .build().create(RedditApi.class);

     return new APIS<>(cacheApi, freshApi);

    }
}