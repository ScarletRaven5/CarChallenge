package com.red.carchallenge.network;


import com.red.carchallenge.model.LocationResult;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface LocationsService {

    @GET("locations")
    Observable<List<LocationResult>> getLocations();

}
