package com.red.carchallenge.injection.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.red.carchallenge.network.LocationsService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class LocationsServiceModule {

    @Provides
    @Singleton
    public LocationsService locationsService(Retrofit locationsRetrofit) {
        return locationsRetrofit.create(LocationsService.class);
    }

    @Provides
    @Singleton
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl("http://localsearch.azurewebsites.net/api/")
                .build();
    }

}
