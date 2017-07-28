package com.red.carchallenge.injection.locationdetailactivity;

import com.red.carchallenge.view.activity.LocationDetailActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationDetailActivityModule {

    private final LocationDetailActivity locationDetailActivity;

    public LocationDetailActivityModule(LocationDetailActivity locationDetailActivity) {
        this.locationDetailActivity = locationDetailActivity;
    }

    @Provides
    @LocationDetailActivityScope
    public LocationDetailActivity locationDetailActivity() {
        return locationDetailActivity;
    }

}
