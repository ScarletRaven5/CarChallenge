package com.red.carchallenge.viewmodel;

import android.view.View;

import com.red.carchallenge.network.LocationsService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class HomeViewModel extends BaseViewModel {
    private LocationsService locationsService;
    private BehaviorSubject<List<LocationViewModel>> locationsSubject = BehaviorSubject.create(new ArrayList<>());
    private BehaviorSubject<Boolean> isLoadingSubject = BehaviorSubject.create(false);

    @Inject
    public HomeViewModel(LocationsService locationsService) {
        this.locationsService = locationsService;
    }

    /**
     * Method to get locations from the API
     *
     * @return The Observable object containing the locations from the API
     */
    public Observable<List<LocationViewModel>> loadLocations() {
        // Don't load if already loading
        if (isLoadingSubject.getValue()) {
            return Observable.empty();
        }

        isLoadingSubject.onNext(true);

        return locationsService
                .getLocations()
                .flatMapIterable(list -> list)
                .map(LocationViewModel::new)
                .toList()
                // Concatenate the new posts to the current posts list, then emit it via the post subject
                .doOnNext(list -> locationsSubject.onNext(locationsSubject.getValue()))
                .doOnTerminate(() -> isLoadingSubject.onNext(false));
    }

    public Observable<Boolean> isLoadingObservable() {
        return isLoadingSubject.asObservable();
    }

    public int getLoadingVisibility(boolean isLoading) {
        return isLoading ? View.VISIBLE : View.GONE;
    }

}
