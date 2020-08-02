package com.blueanvil.durationstr;

import org.testng.annotations.Test;

import java.time.Duration;

import static com.blueanvil.Duration_parseKt.toDuration;
import static com.blueanvil.Duration_printKt.toHumanReadableString;

/**
 * @author Cosmin Marginean
 */
public class JavaTest {

    @Test
    public void test() {
        Duration duration = toDuration("24d 3h 45m");
        toHumanReadableString(duration);
    }
}
