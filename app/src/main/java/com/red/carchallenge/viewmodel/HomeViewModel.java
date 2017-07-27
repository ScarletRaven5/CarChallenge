package com.red.carchallenge.viewmodel;

import android.view.View;

import com.red.carchallenge.network.LocationsService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class HomeViewModel {
    private LocationsService locationsService;
    private BehaviorSubject<List<LocationViewModel>> locationsSubject = BehaviorSubject.create(new ArrayList<>());
    private BehaviorSubject<Boolean> isLoadingSubject = BehaviorSubject.create(false);


    @Inject
    public HomeViewModel(LocationsService locationsService) {
        this.locationsService = locationsService;
    }

    public Observable<List<LocationViewModel>> loadLocations() {
        // Don't try and load if we're already loading
        if (isLoadingSubject.getValue()) {
            return Observable.empty();
        }

        isLoadingSubject.onNext(true);

        return locationsService
                .getLocations()
                .flatMapIterable(list -> list)
                // Transform model to viewmodel
                .map(location -> new LocationViewModel(location))
                // Merge viewmodels into a single list to be emitted
                .toList()
                // Concatenate the new posts to the current posts list, then emit it via the post subject
                .doOnNext(list -> locationsSubject.onNext(locationsSubject.getValue()))
                .doOnTerminate(() -> isLoadingSubject.onNext(false));
    }

    public Observable<List<LocationViewModel>> locationsObservable() {
        return locationsSubject.asObservable();
    }

    public Observable<Boolean> isLoadingObservable() {
        return isLoadingSubject.asObservable();
    }

    public int getLoadingVisibility(boolean isLoading) {
        return isLoading ? View.VISIBLE : View.GONE;
    }
}
