package com.red.carchallenge;

import android.support.test.rule.ActivityTestRule;

import com.red.carchallenge.model.LocationResult;
import com.red.carchallenge.network.LocationsService;
import com.red.carchallenge.view.activity.HomeActivity;
import com.red.carchallenge.viewmodel.HomeViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomeActivityTest extends ActivityTestRule {

    private LocationsService testLocationsService;
    private HomeViewModel testViewModel;
    private HomeActivity testHomeActivity;

    @Rule
    public final ActivityRule<HomeActivity> rule = new ActivityRule<>(HomeActivity.class);

    public HomeActivityTest() {
        super(HomeActivityTest.class);
    }

    @Before
    public void setUp() {
        testHomeActivity = rule.get();
        testLocationsService = mock(LocationsService.class);
        testViewModel = spy(new HomeViewModel(testLocationsService));
    }

    @Test
    public void testViewsThatMustBeVisible() {
        onView(withId(R.id.recycler_locations)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void testLocationItemGoesToLocationDetail() {
        onView(allOf(withId(R.id.name_location), withText("Cubby Bear"))).perform(click());
        // Check the next activity (LocationDetailActivity) launched successfully  with confirmed view from LaunchDetailActivity shown
        onView(withId(R.id.text_title)).check(matches(isDisplayed()));
    }

    @Test
    public void testShouldLoadLocationResults() {
        Observable<List<LocationResult>> testObservable = (Observable<List<LocationResult>>) mock(Observable.class);

        // Cannot figure out why it thinks testLocationsService or testObservables aren't mocks, so test fails
        when(testLocationsService.getLocations()).thenReturn(testObservable);
        when(testObservable.subscribeOn(Schedulers.io())).thenReturn(testObservable);
        when(testObservable.observeOn(AndroidSchedulers.mainThread())).thenReturn(testObservable);

        testViewModel.loadLocations();
        verify(testViewModel).loadLocations();

        verify(testObservable).subscribeOn(Schedulers.io());
        verify(testObservable).observeOn(AndroidSchedulers.mainThread());
    }

}
