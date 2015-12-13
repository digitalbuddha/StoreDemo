package com.nytimes.storedemo;

import com.nytimes.storedemo.di.AppComponent;
import com.nytimes.storedemo.di.DaggerAppComponent;

public class DemoApplication extends android.app.Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .build();
    }


    public AppComponent getApplicationComponent() {
        return component;
    }
}