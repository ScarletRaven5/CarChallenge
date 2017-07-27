package com.red.carchallenge.injection;

import com.red.carchallenge.screens.HomeActivity;

import dagger.Component;

@HomeActivityScope
@Component(modules = HomeActivityModule.class, dependencies = AppComponent.class)
public interface HomeActivityComponent {

    void injectHomeActivity(HomeActivity homeActivity);
}