package com.nytimes.storedemo;

import com.nytimes.storedemo.di.AppComponent;
import com.nytimes.storedemo.di.DaggerAppComponent;
import com.nytimes.storedemo.di.StoreModule;

public class DemoApplication extends android.app.Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .storeModule(new StoreModule(this))
                .build();
    }

    public AppComponent getApplicationComponent() {
        return component;
    }
}