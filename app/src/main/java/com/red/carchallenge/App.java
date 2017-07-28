package com.red.carchallenge;

import android.app.Activity;
import android.app.Application;

import com.red.carchallenge.injection.app.ApplicationComponent;
import com.red.carchallenge.injection.app.ContextModule;
import com.red.carchallenge.injection.app.DaggerApplicationComponent;

import timber.log.Timber;

public class App extends Application {

    private ApplicationComponent applicationComponent;

    public static App get(Activity activity) {
        return (App) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        applicationComponent = DaggerApplicationComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}