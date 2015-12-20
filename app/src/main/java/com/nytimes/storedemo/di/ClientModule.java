package com.nytimes.storedemo.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nytimes.storedemo.model.GsonAdaptersModel;
import com.nytimes.storedemo.rest.APIS;
import com.nytimes.storedemo.rest.RedditApi;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

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
    APIS<RedditApi> provideRedditApi(OkHttpClient client, Gson gson) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://reddit.com/");

        Retrofit retrofit = builder
                .client(client)
                .build();
        RedditApi cacheApi = retrofit.create(RedditApi.class);

        RedditApi freshApi = builder
                .client(new OkHttpClient())//no cache
                .build()
                .create(RedditApi.class);


        return new APIS<>(cacheApi, freshApi);

    }
}