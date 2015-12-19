package com.nytimes.storedemo.di;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nytimes.storedemo.model.GsonAdaptersModel;
import com.nytimes.storedemo.rest.RedditApi;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

@Module
public class AppModule {

    private Application context;

    public AppModule(Application context) {
        this.context = context;
    }

    @Singleton
    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(new GsonAdaptersModel());
        return gsonBuilder.create();
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return context;
    }

    @Singleton
    @Provides
    RedditApi provideRedditApi() {
        OkHttpClient okHttpClient = new OkHttpClient();
        File cacheDir = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
        try {
            okHttpClient.setCache(new Cache(cacheDir, 1024));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Create a Retrofit RestAdapter for our SodaService interface.
        Executor executor = Executors.newCachedThreadPool();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setExecutors(executor, executor)
                .setClient(new OkClient(okHttpClient))
                .setEndpoint("reddit.com")
                .build();
        return restAdapter.create(RedditApi.class);
    }

}