package com.red.carchallenge.injection.app;

import com.red.carchallenge.injection.ActivityModule;
import com.red.carchallenge.network.LocationsService;
import com.red.carchallenge.network.LocationsServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, LocationsServiceModule.class, ActivityModule.class})
public interface ApplicationComponent {

    LocationsService getLocationsService();
}
