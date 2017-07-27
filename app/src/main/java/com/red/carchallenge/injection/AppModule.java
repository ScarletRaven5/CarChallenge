package com.red.carchallenge.injection;

import com.red.carchallenge.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @AppContext
    public App getAppContext() {
        return application;
    }
}
