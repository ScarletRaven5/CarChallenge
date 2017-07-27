package com.red.carchallenge;

import android.app.Activity;
import android.app.Application;

import com.red.carchallenge.injection.AppComponent;
import com.red.carchallenge.injection.ContextModule;
import com.red.carchallenge.injection.DaggerAppComponent;
import com.red.carchallenge.network.LocationsService;

import timber.log.Timber;

public class App extends Application {

    private AppComponent appComponent;
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

        appComponent = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        locationsService = appComponent.getLocationsService();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}