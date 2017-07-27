package com.red.carchallenge.injection;

import com.red.carchallenge.screens.LocationDetailActivity;

import dagger.Component;

@HomeActivityScope
@Component(modules = LocationDetailActivityModule.class, dependencies = AppComponent.class)
public interface LocationDetailActivityComponent {

    void injectLocationDetailActivity(LocationDetailActivity locationDetailActivity);
}