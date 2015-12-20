package com.digitalbuddha.daodemo;

import com.digitalbuddha.daodemo.di.AppComponent;
import com.digitalbuddha.daodemo.base.di.DAOModule;
import com.digitalbuddha.daodemo.di.DaggerAppComponent;
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
                .dAOModule(new DAOModule(this))
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