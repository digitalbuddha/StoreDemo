package com.nytimes.storedemo.di;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nytimes.storedemo.model.GsonAdaptersArticle;
import com.nytimes.storedemo.model.GsonAdaptersArticleEnvelope;
import com.nytimes.storedemo.model.GsonAdaptersImage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
        gsonBuilder.registerTypeAdapterFactory(new GsonAdaptersArticleEnvelope());
        gsonBuilder.registerTypeAdapterFactory(new GsonAdaptersArticle());
        gsonBuilder.registerTypeAdapterFactory(new GsonAdaptersImage());

        return gsonBuilder.create();
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return context;
    }

}