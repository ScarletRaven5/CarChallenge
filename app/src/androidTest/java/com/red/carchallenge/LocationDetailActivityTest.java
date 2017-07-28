package com.red.carchallenge;

import android.support.test.rule.ActivityTestRule;

import com.red.carchallenge.model.LocationResult;
import com.red.carchallenge.network.LocationsService;
import com.red.carchallenge.view.activity.HomeActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.mock;

public class LocationDetailActivityTest extends ActivityTestRule {

    private LocationsService testLocationsService;
    private HomeActivity testHomeActivity;
    private LocationResult testLocationResult;

    @Rule
    public final ActivityRule<HomeActivity> rule = new ActivityRule<>(HomeActivity.class);

    public LocationDetailActivityTest() {
        super(LocationDetailActivityTest.class);
    }

    @Before
    public void setUp() {
        // Start out on HomeActivity and click into LocationDetailActivity to get valid LocationResult
        testHomeActivity = rule.get();
        testLocationsService = mock(LocationsService.class);
        onView(allOf(withId(R.id.name_location), withText("Cubby Bear"))).perform(click());
        testLocationResult = mock(LocationResult.class);
    }

    @Test
    public void testViewsThatMustBeVisible() {
        onView(withId(R.id.image_map)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.text_arrival_time)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.text_title)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.text_address)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.text_latitude)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.text_longitude)).check(matches(isCompletelyDisplayed()));
    }
}
