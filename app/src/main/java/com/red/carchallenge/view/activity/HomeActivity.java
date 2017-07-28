package com.red.carchallenge.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.google.android.gms.maps.model.LatLng;
import com.red.carchallenge.App;
import com.red.carchallenge.R;
import com.red.carchallenge.injection.homeactivity.DaggerHomeActivityComponent;
import com.red.carchallenge.injection.homeactivity.HomeActivityComponent;
import com.red.carchallenge.injection.homeactivity.HomeActivityModule;
import com.red.carchallenge.view.LocationsAdapter;
import com.red.carchallenge.viewmodel.HomeViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class HomeActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    @BindView(R.id.recycler_locations)
    RecyclerView locationsRecycler;
    @BindView(R.id.spinner_loading)
    ProgressBar loadingSpinner;

    @Inject
    HomeViewModel viewModel;

    private CompositeSubscription subscriptions;
    private LocationsAdapter locationsAdapter;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        HomeActivityComponent component = DaggerHomeActivityComponent
                .builder()
                .homeActivityModule(new HomeActivityModule(this))
                .applicationComponent(App.get(this).getApplicationComponent())
                .build();

        component.injectHomeActivity(this);

        subscriptions = new CompositeSubscription();
        locationsAdapter = new LocationsAdapter();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_name:
                locationsAdapter.sortByName();
                return true;
            case R.id.sort_distance:
                requestAndFindLocation();
                return true;
            case R.id.sort_time:
                locationsAdapter.sortByTime();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void init() {
        initViews();
        initSubscriptions();
    }

    private void initViews() {
        locationsRecycler.setLayoutManager(new LinearLayoutManager(this));
        locationsRecycler.setAdapter(locationsAdapter);
    }

    private void initSubscriptions() {
        subscriptions.addAll(
                subscribeToLocationResults(),
                subscribeToLoadingStatus()
        );
    }

    private Subscription subscribeToLocationResults() {
        return viewModel.loadLocations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(locationsAdapter::setItems,
                        this::handleError);
    }

    private Subscription subscribeToLoadingStatus() {
        return viewModel.isLoadingObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setIsLoadingIndicator);
    }

    private void handleError(Throwable throwable) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getResources().getString(R.string.title_error));
        alertDialog.setMessage(throwable.getMessage());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                getResources().getString(R.string.retry),
                (dialog, which) -> init());
        alertDialog.show();
    }

    private void setIsLoadingIndicator(boolean isLoading) {
        loadingSpinner.setVisibility(viewModel.getLoadingVisibility(isLoading));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestAndFindLocation();
                }
            }
        }
    }

    private void requestAndFindLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        } else {
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            LatLng currentLatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            locationsAdapter.sortByDistance(currentLatLng);
        }
    }

}
