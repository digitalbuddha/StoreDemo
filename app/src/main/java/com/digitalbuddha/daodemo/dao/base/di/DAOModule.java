package com.digitalbuddha.daodemo.dao.base.di;

import android.app.Application;

import com.digitalbuddha.daodemo.dao.base.di.anotation.ClientCache;
import com.digitalbuddha.daodemo.util.NetworkStatus;
import com.google.gson.Gson;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
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
    OkHttpClient provideCachedClient(@ClientCache File cacheDir,Interceptor interceptor) {

        Cache cache = new Cache(cacheDir, 20 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(cache);
        okHttpClient.interceptors().add(interceptor);
        okHttpClient.networkInterceptors().add(interceptor);
        return okHttpClient;
    }
    @Singleton
    @Provides
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
                .client(cachedClient)
                .baseUrl("http://reddit.com/");
    }

    @Singleton
    @Provides
    Picasso providePicasso(OkHttpClient client, Application context) {
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttpDownloader(client));
        return builder.build();
    }
}