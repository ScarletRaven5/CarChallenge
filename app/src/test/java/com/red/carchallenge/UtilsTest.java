package com.red.carchallenge;


import com.red.carchallenge.util.Utils;

import static org.junit.Assert.*;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void testArrivalTimeInMillis() {
        long actual = Utils.getArrivalTimeInMillis("2017-07-27T20:25:39.203");
        long expected = 1501187139203L;
        assertEquals("Convert arrival time from API returned format to milliseconds ", expected, actual, 100);
    }
}
