package com.digitalbuddha.storedemo;

import com.digitalbuddha.storedemo.di.AppComponent;
import com.digitalbuddha.storedemo.di.DaggerAppComponent;
import com.digitalbuddha.storedemo.di.StoreModule;
import com.digitalbuddha.storedemo.di.anotation.ImageCache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Inject;

public class DemoApplication extends android.app.Application {

    private AppComponent component;

    @Inject
    @ImageCache
    protected File imageCache;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .storeModule(new StoreModule(this))
                .build();

        component.inject(this);
        configurePIcasso();
    }

    private void configurePIcasso() {
        long CACHE_SIZE = 1024 * 1024 * 100; // 75MB
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(imageCache, CACHE_SIZE));
        Picasso built = builder.build();
        //MIKE: keep these lines please sir
        //built.setIndicatorsEnabled(true);
        //built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }

    public AppComponent getApplicationComponent() {
        return component;
    }
}