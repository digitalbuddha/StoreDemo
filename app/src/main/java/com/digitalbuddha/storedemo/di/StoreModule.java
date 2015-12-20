package com.digitalbuddha.storedemo.di;

import android.app.Application;

import com.digitalbuddha.storedemo.di.anotation.CachedOKHTTP;
import com.digitalbuddha.storedemo.di.anotation.ClientCache;
import com.digitalbuddha.storedemo.di.anotation.FreshOKHTTP;
import com.digitalbuddha.storedemo.di.anotation.ImageCache;
import com.digitalbuddha.storedemo.util.NetworkStatus;
import com.google.gson.Gson;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

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
    @FreshOKHTTP
    OkHttpClient provideFreshClient(@ClientCache File cacheDir, @FreshOKHTTP Interceptor interceptor) {

        Cache cache = new Cache(cacheDir, 20 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(cache);
        okHttpClient.interceptors().add(interceptor);
        okHttpClient.networkInterceptors().add(interceptor);
        return okHttpClient;
    }
    @Singleton
    @Provides
    @CachedOKHTTP
    OkHttpClient provideCachedClient(@ClientCache File cacheDir, @CachedOKHTTP Interceptor interceptor) {

        Cache cache = new Cache(cacheDir, 20 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(cache);
        okHttpClient.interceptors().add(interceptor);
        okHttpClient.networkInterceptors().add(interceptor);
        return okHttpClient;
    }
    @Singleton
    @Provides
    @CachedOKHTTP
    Interceptor provideCachedInteceptor(NetworkStatus networkStatus) {
        return chain -> {
            Request originalRequest = chain.request();
            String cacheHeaderValue = networkStatus.isOnGoodConnection()
                    ? "public, max-age=2419200"
                    : "public, only-if-cached, max-stale=2419200";
            Request request = originalRequest.newBuilder().build();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheHeaderValue)
                    .build();
        };
    }

    @Singleton
    @Provides
    @FreshOKHTTP
    Interceptor provideFreshInteceptor() {
        return chain -> {
            Request originalRequest = chain.request();
            String cacheHeaderValue = "public, no-cache";
            Request request = originalRequest.newBuilder().build();

            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheHeaderValue)
                    .build();
        };
    }

    @Singleton
    @Provides
    @ClientCache
    File provideCacheFile(Application context) {
        return new File(context.getCacheDir(), "cache_file");
    }

    @Singleton
    @Provides
    @ImageCache
    File provideImageCacheFile() {
        return new File(context.getCacheDir(), "image_cache_file");
    }


    @Singleton
    @Provides
    Retrofit.Builder provideRestAdapterBuilder(Gson gson, @CachedOKHTTP OkHttpClient cachedClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(cachedClient)
                .baseUrl("http://reddit.com/");
    }
}