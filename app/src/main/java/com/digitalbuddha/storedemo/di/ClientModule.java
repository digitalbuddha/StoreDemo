package com.digitalbuddha.storedemo.di;

import com.digitalbuddha.storedemo.model.GsonAdaptersModel;
import com.digitalbuddha.storedemo.rest.RedditApi;
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
                .build();
        return retrofit.create(RedditApi.class);
    }
}