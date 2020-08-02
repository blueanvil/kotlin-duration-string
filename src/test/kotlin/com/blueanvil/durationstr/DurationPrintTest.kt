package com.blueanvil

import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import java.time.Duration

/**
 * @author Cosmin Marginean
 */
class DurationPrintTest {

    @Test
    fun simpleDuration() {
        assertEquals(Duration.ofDays(16).toHumanReadableString(), "16d")
        assertEquals(Duration.ofHours(23).toHumanReadableString(), "23h")
        assertEquals(Duration.ofMinutes(43).toHumanReadableString(), "43m")
        assertEquals(Duration.ofSeconds(5).toHumanReadableString(), "5s")
        assertEquals(Duration.ofMillis(762).toHumanReadableString(), "762ms")
    }

    @Test
    fun combinedDuration() {
        assertEquals(Duration.ofDays(16).plus(Duration.ofHours(23)).toHumanReadableString(), "16d 23h")
        assertEquals(Duration.ofDays(16).plus(Duration.ofHours(23)).plus(Duration.ofMinutes(45)).toHumanReadableString(), "16d 23h 45m")
        assertEquals(Duration.ofDays(16).plus(Duration.ofHours(23)).plus(Duration.ofMinutes(45)).plus(Duration.ofSeconds(12)).toHumanReadableString(),
                "16d 23h 45m 12s")
        assertEquals(Duration.ofDays(16).plus(Duration.ofHours(23)).plus(Duration.ofMinutes(45))
                .plus(Duration.ofSeconds(12).plus(Duration.ofMillis(345))).toHumanReadableString(),
                "16d 23h 45m 12s 345ms")


        assertEquals(Duration.ofHours(23).plus(Duration.ofMinutes(45))
                .plus(Duration.ofSeconds(12).plus(Duration.ofMillis(345))).toHumanReadableString(),
                "23h 45m 12s 345ms")
        assertEquals(Duration.ofMinutes(45).plus(Duration.ofSeconds(12).plus(Duration.ofMillis(345))).toHumanReadableString(), "45m 12s 345ms")
        assertEquals(Duration.ofSeconds(12).plus(Duration.ofMillis(345)).toHumanReadableString(), "12s 345ms")
        assertEquals(Duration.ofMillis(345).toHumanReadableString(), "345ms")
    }

    @Test
    fun printZerosBetweenNonZeros() {
        assertEquals(Duration.ofDays(16).plus(Duration.ofMinutes(45)).plus(Duration.ofSeconds(12).plus(Duration.ofMillis(345))).toHumanReadableString(),
                "16d 0h 45m 12s 345ms")
        assertEquals(Duration.ofDays(16).plus(Duration.ofSeconds(12).plus(Duration.ofMillis(345))).toHumanReadableString(),
                "16d 0h 0m 12s 345ms")
        assertEquals(Duration.ofDays(16).plus(Duration.ofMillis(345)).toHumanReadableString(),
                "16d 0h 0m 0s 345ms")
        assertEquals(Duration.ofHours(16).plus(Duration.ofSeconds(27)).toHumanReadableString(),
                "16h 0m 27s")
    }

    @Test
    fun testSymmetricOps() {
        assertEquals("16d".toDuration()!!.toHumanReadableString(), "16d")
        assertEquals("16d 5h".toDuration()!!.toHumanReadableString(), "16d 5h")
        assertEquals("16d 5h 23m".toDuration()!!.toHumanReadableString(), "16d 5h 23m")
        assertEquals("16d 5h 23m 45s".toDuration()!!.toHumanReadableString(), "16d 5h 23m 45s")
        assertEquals("16d 5h 23m 45s 124ms".toDuration()!!.toHumanReadableString(), "16d 5h 23m 45s 124ms")
        assertEquals("16d 0h 23m 45s 124ms".toDuration()!!.toHumanReadableString(), "16d 0h 23m 45s 124ms")
        assertEquals("16d 0h 23m 45s 0ms".toDuration()!!.toHumanReadableString(), "16d 0h 23m 45s")
        assertEquals("16d 0h 23m 0s 0ms".toDuration()!!.toHumanReadableString(), "16d 0h 23m")
        assertEquals("16d 0h 0m 0s 0ms".toDuration()!!.toHumanReadableString(), "16d")
        assertEquals("124ms".toDuration()!!.toHumanReadableString(), "124ms")
        assertEquals("45m 124ms".toDuration()!!.toHumanReadableString(), "45m 0s 124ms")
        assertEquals("29s 124ms".toDuration()!!.toHumanReadableString(), "29s 124ms")

        assertEquals("24h".toDuration()!!.toHumanReadableString(), "1d")
        assertEquals("3600s".toDuration()!!.toHumanReadableString(), "1h")
        assertEquals("7200000ms".toDuration()!!.toHumanReadableString(), "2h")
        assertEquals("7200m".toDuration()!!.toHumanReadableString(), "5d")
    }
}