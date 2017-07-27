package com.red.carchallenge.injection.locationdetailactivity;

import com.red.carchallenge.injection.app.ApplicationComponent;
import com.red.carchallenge.injection.homeactivity.HomeActivityScope;
import com.red.carchallenge.screens.LocationDetailActivity;

import dagger.Component;

@HomeActivityScope
@Component(modules = LocationDetailActivityModule.class, dependencies = ApplicationComponent.class)
public interface LocationDetailActivityComponent {

    void injectLocationDetailActivity(LocationDetailActivity locationDetailActivity);
}