package com.red.carchallenge;


import com.red.carchallenge.util.Utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void testArrivalTimeInMillis() {
        long actual = Utils.getArrivalTimeInMillis("2017-07-27T20:25:39.203");
        long expected = 1501187139203L;
        assertEquals("Convert arrival time from API returned format to milliseconds", expected, actual, 100);
    }

    @Test
    public void testGetArrivalTimeCategory() {
        int actualOver = Utils.getArrivalTimeCategory(15000000);
        int expectedOver = 1;
        assertEquals("Time is an hour or over", expectedOver, actualOver, 0);

        int actualUnder = Utils.getArrivalTimeCategory(1500000);
        int expectedUnder = 2;
        assertEquals("Time is under an hour", expectedUnder, actualUnder, 0);
    }
}
