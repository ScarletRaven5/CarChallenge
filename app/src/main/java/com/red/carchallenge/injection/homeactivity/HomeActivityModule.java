package com.red.carchallenge.injection.homeactivity;

import com.red.carchallenge.screens.HomeActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeActivityModule {

    private final HomeActivity homeActivity;

    public HomeActivityModule(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Provides
    @HomeActivityScope
    public HomeActivity homeActivity() {
        return homeActivity;
    }
}
