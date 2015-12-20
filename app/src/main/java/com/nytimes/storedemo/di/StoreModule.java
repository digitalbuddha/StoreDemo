package com.nytimes.storedemo.di;

import android.app.Application;

import com.nytimes.storedemo.di.anotation.ClientCache;
import com.nytimes.storedemo.di.anotation.ImageCache;
import com.nytimes.storedemo.util.NetworkStatus;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    OkHttpClient provideClient(@ClientCache File cacheDir, Interceptor interceptor) {

        Cache cache = new Cache(cacheDir, 20 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(cache);
        okHttpClient.interceptors().add(interceptor);
        okHttpClient.networkInterceptors().add(interceptor);
        return okHttpClient;
    }

    @Singleton
    @Provides
    Interceptor provideInteceptor(NetworkStatus networkStatus) {
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

//
//    @Singleton
//    @Provides
//    RestAdapter.Builder provideRestAdapterBuilder(OkHttpClient client, Gson gson) {
//        Executor executor = Executors.newCachedThreadPool();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://api.nuuneoi.com/base/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        return new Retrofit().Builder()
//                .setExecutors(executor, executor)
//                .setClient(new OkClient(client))
//                .setConverter(new GsonConverter(gson));
//    }
}