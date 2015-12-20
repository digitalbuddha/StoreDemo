package com.digitalbuddha.storedemo;

import com.digitalbuddha.storedemo.di.AppComponent;
import com.digitalbuddha.storedemo.di.DaggerAppComponent;
import com.digitalbuddha.storedemo.di.StoreModule;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class DemoApplication extends android.app.Application {

    private AppComponent component;
    @Inject
    Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        component = DaggerAppComponent.builder()
                .storeModule(new StoreModule(this))
                .build();

        component.inject(this);

        //PICASSO SETUP and debug if desired
        //picasso.setIndicatorsEnabled(true);
        //picasso.setLoggingEnabled(true);
        Picasso.setSingletonInstance(picasso);
    }

    public AppComponent getApplicationComponent() {
        return component;
    }
}