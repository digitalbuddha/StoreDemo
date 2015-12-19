package com.nytimes.storedemo.di;

import android.app.Application;

import com.google.gson.Gson;
import com.nytimes.storedemo.util.NetworkStatus;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

@Module
public class StoreModule {

    private Application context;

    public StoreModule(Application context) {
        this.context = context;
    }


    @Singleton
    @Provides
    Application provideApplication() {
        return context;
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(File cacheDir) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(new Cache(cacheDir, 1024));
        return okHttpClient;
    }

    @Singleton
    @Provides
    File provideCacheFile() {
        return new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
    }

    @Singleton
    @Provides
    RequestInterceptor provideInteceptor(NetworkStatus network) {
        return request -> {
            request.addHeader("Accept", "application/json;versions=1");
            if (network.isOnGoodConnection()) {
                int maxAge = 60; // read from cache for 1 minute
                request.addHeader("Cache-Control", "public, max-age=" + maxAge);
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                request.addHeader("Cache-Control",
                        "public, only-if-cached, max-stale=" + maxStale);
            }
        };
    }

    @Singleton
    @Provides
    RestAdapter.Builder provideRestAdapterBuilder(OkHttpClient client, Gson gson, RequestInterceptor interceptor) {
        Executor executor = Executors.newCachedThreadPool();
        return new RestAdapter.Builder()
                .setExecutors(executor, executor)
                .setClient(new OkClient(client))
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(interceptor);
    }
}