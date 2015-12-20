package com.digitalbuddha.storedemo;

import com.digitalbuddha.storedemo.di.AppComponent;
import com.digitalbuddha.storedemo.di.DaggerAppComponent;
import com.digitalbuddha.storedemo.di.StoreModule;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class DemoApplication extends android.app.Application {

    private AppComponent component;
    @Inject
    Picasso picasso;


    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .storeModule(new StoreModule(this))
                .build();

        component.inject(this);
        Picasso.setSingletonInstance(picasso);
    }

    private void configurePIcasso() {
        //MIKE: keep these lines please sir
        //built.setIndicatorsEnabled(true);
        //built.setLoggingEnabled(true);
    }


    public AppComponent getApplicationComponent() {
        return component;
    }
}