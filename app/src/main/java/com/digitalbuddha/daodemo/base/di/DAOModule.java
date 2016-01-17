package com.digitalbuddha.daodemo.base.di;

import android.app.Application;

import com.digitalbuddha.daodemo.base.CacheInterceptor;
import com.digitalbuddha.daodemo.base.di.anotation.ClientCache;
import com.google.gson.Gson;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module
public class DAOModule {

    private Application context;

    public DAOModule(Application context) {
        this.context = context;
    }


    @Singleton
    @Provides
    Application provideApplication() {
        return context;
    }


    @Singleton
    @Provides
    OkHttpClient provideCachedClient(@ClientCache File cacheDir,CacheInterceptor interceptor) {

        Cache cache = new Cache(cacheDir, 20 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(cache);
        okHttpClient.interceptors().add(interceptor);
        okHttpClient.networkInterceptors().add(interceptor);
        return okHttpClient;
    }


    @Singleton
    @Provides
    @ClientCache
    File provideCacheFile(Application context) {
        return new File(context.getCacheDir(), "cache_file");
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRestAdapterBuilder(Gson gson, OkHttpClient cachedClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(cachedClient);
    }

    @Singleton
    @Provides
    Picasso providePicasso(OkHttpClient client, Application context) {
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttpDownloader(client));
        return builder.build();
    }
}