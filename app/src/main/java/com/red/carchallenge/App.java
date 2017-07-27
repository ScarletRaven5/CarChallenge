package com.red.carchallenge;

import android.app.Activity;
import android.app.Application;

import com.red.carchallenge.injection.app.ApplicationComponent;
import com.red.carchallenge.injection.app.ContextModule;
import com.red.carchallenge.injection.app.DaggerApplicationComponent;
import com.red.carchallenge.network.LocationsService;

import timber.log.Timber;

public class App extends Application {

    private ApplicationComponent applicationComponent;
    private LocationsService locationsService;

    public static App get(Activity activity) {
        return (App) activity.getApplication();
    }


    //   Activity

    //LocationsService   picasso

    //retrofit    OkHttp3Downloader

    //gson      okhttp

    //      logger    cache

    //      timber           file

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        applicationComponent = DaggerApplicationComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        locationsService = applicationComponent.getLocationsService();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}