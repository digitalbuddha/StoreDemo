package com.digitalbuddha.daodemo.di;

import com.digitalbuddha.daodemo.reddit.data.model.GsonAdaptersModel;
import com.digitalbuddha.daodemo.reddit.data.RedditApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

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
    RedditApi provideRedditApi(Retrofit.Builder builder) {

        //create an api with okhttp cache
        Retrofit retrofit = builder
                .baseUrl("http://reddit.com/")
                .build();
        return retrofit.create(RedditApi.class);
    }
}