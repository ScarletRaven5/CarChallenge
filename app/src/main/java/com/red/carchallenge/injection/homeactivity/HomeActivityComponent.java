package com.red.carchallenge.injection.homeactivity;

import com.red.carchallenge.injection.app.ApplicationComponent;
import com.red.carchallenge.screens.HomeActivity;

import dagger.Component;

@HomeActivityScope
@Component(modules = HomeActivityModule.class, dependencies = ApplicationComponent.class)
public interface HomeActivityComponent {

    void injectHomeActivity(HomeActivity homeActivity);
}