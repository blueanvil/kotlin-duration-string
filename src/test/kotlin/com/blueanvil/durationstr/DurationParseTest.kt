package com.blueanvil

import org.testng.Assert.*
import org.testng.annotations.Test
import java.time.Duration

/**
 * @author Cosmin Marginean
 */
class DurationParseTest {

    @Test
    fun regexMatch() {
        assertTrue(REGEX_DURATION.matches("24d"))
        assertTrue(REGEX_DURATION.matches("45m"))
        assertTrue(REGEX_DURATION.matches("3h"))
        assertTrue(REGEX_DURATION.matches("56s"))
        assertTrue(REGEX_DURATION.matches("345ms"))

        assertTrue(REGEX_DURATION.matches("024d"))
        assertTrue(REGEX_DURATION.matches("045m"))
        assertTrue(REGEX_DURATION.matches("03h"))
        assertTrue(REGEX_DURATION.matches("056s"))
        assertTrue(REGEX_DURATION.matches("0345ms"))

        assertTrue(REGEX_DURATION.matches("24d 3h"))
        assertTrue(REGEX_DURATION.matches("24d 3h 45m"))
        assertTrue(REGEX_DURATION.matches("24d 3h 45m 56s"))
        assertTrue(REGEX_DURATION.matches("24d 3h 45m 56s 345ms"))
        assertTrue(REGEX_DURATION.matches("345ms"))
        assertTrue(REGEX_DURATION.matches("56s 345ms"))
        assertTrue(REGEX_DURATION.matches("45m 56s 345ms"))
        assertTrue(REGEX_DURATION.matches("3h 45m 56s 345ms"))
        assertTrue(REGEX_DURATION.matches("3h 45m 56s 999ms"))
        assertTrue(REGEX_DURATION.matches("3h 45m 59s 999ms"))
        assertTrue(REGEX_DURATION.matches("3h 59m 59s 999ms"))

        assertTrue(REGEX_DURATION.matches("024d 03h"))
        assertTrue(REGEX_DURATION.matches("024d 03h 045m"))
        assertTrue(REGEX_DURATION.matches("24d 03h 45m 56s"))
        assertTrue(REGEX_DURATION.matches("24d 3h 045m 056s 0345ms"))
        assertTrue(REGEX_DURATION.matches("0345ms"))
        assertTrue(REGEX_DURATION.matches("56s 345ms"))
        assertTrue(REGEX_DURATION.matches("045m 56s 345ms"))
        assertTrue(REGEX_DURATION.matches("03h 45m 56s 345ms"))
        assertTrue(REGEX_DURATION.matches("03h 45m 056s 999ms"))
        assertTrue(REGEX_DURATION.matches("03h 045m 059s 999ms"))
        assertTrue(REGEX_DURATION.matches("03h 059m 59s 999ms"))
        assertTrue(REGEX_DURATION.matches("24d 03h 059m 59s 999ms"))
        assertTrue(REGEX_DURATION.matches("24d 03h 0m 0s 0ms"))

        assertTrue(REGEX_DURATION.matches("98723h"))
        assertTrue(REGEX_DURATION.matches("29123m"))
        assertTrue(REGEX_DURATION.matches("165s"))
        assertTrue(REGEX_DURATION.matches("2397165ms"))
        assertTrue(REGEX_DURATION.matches("24h"))
        assertTrue(REGEX_DURATION.matches("60m"))
        assertTrue(REGEX_DURATION.matches("1000s"))

        assertTrue(REGEX_DURATION.matches("0098723h"))
        assertTrue(REGEX_DURATION.matches("029123m"))
        assertTrue(REGEX_DURATION.matches("00165s"))
        assertTrue(REGEX_DURATION.matches("002397165ms"))
        assertTrue(REGEX_DURATION.matches("0024h"))
        assertTrue(REGEX_DURATION.matches("060m"))
        assertTrue(REGEX_DURATION.matches("01000s"))

        assertFalse(REGEX_DURATION.matches("29123m 45s"))
        assertFalse(REGEX_DURATION.matches("98723h 45s"))
        assertFalse(REGEX_DURATION.matches("98723h 9212m 45s"))
        assertFalse(REGEX_DURATION.matches("24d 3h 45m 156s"))
        assertFalse(REGEX_DURATION.matches("24d 3h 45m 4s 9328742ms"))

        assertFalse(REGEX_DURATION.matches("65d 24h"))
        assertFalse(REGEX_DURATION.matches("65d 24h 60m"))
        assertFalse(REGEX_DURATION.matches("65d 24h 60m 60s"))
        assertFalse(REGEX_DURATION.matches("5m 60s"))
        assertFalse(REGEX_DURATION.matches("5m 1000s"))
    }

    @Test
    fun parseDuration() {
        assertEquals("16d".toDuration(), Duration.ofDays(16))
        assertEquals("016d".toDuration(), Duration.ofDays(16))
        assertEquals("4h".toDuration(), Duration.ofHours(4))
        assertEquals("04h".toDuration(), Duration.ofHours(4))
        assertEquals("94h".toDuration(), Duration.ofHours(94))
        assertEquals("094h".toDuration(), Duration.ofHours(94))
        assertEquals("0094h".toDuration(), Duration.ofHours(94))
        assertEquals("5m".toDuration(), Duration.ofMinutes(5))
        assertEquals("05m".toDuration(), Duration.ofMinutes(5))
        assertEquals("0155m".toDuration(), Duration.ofMinutes(155))
        assertEquals("2374s".toDuration(), Duration.ofSeconds(2374))
        assertEquals("489ms".toDuration(), Duration.ofMillis(489))
        assertEquals("324489ms".toDuration(), Duration.ofMillis(324489))

        assertEquals("16d 4h".toDuration(), Duration.ofDays(16).plus(Duration.ofHours(4)))
        assertEquals("16d 04h".toDuration(), Duration.ofDays(16).plus(Duration.ofHours(4)))
        assertEquals("16d 4h 35s".toDuration(), Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)))
        assertEquals("16d 04h 035s".toDuration(), Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)))
        assertEquals("16d 4h 35s 782ms".toDuration(), Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)).plus(Duration.ofMillis(782)))
        assertEquals("16d 4h 35s 0782ms".toDuration(), Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)).plus(Duration.ofMillis(782)))
        assertEquals("16d 04h 035s 0782ms".toDuration(), Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)).plus(Duration.ofMillis(782)))
        assertEquals("16d 04h 035s 0782ms".toDuration(), Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)).plus(Duration.ofMillis(782)))
        assertEquals("0016d 04h 035s 0782ms".toDuration(), Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)).plus(Duration.ofMillis(782)))
        assertEquals("\t16d \t\n04h  \n35s \r 782ms \r\r".toDuration(), Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)).plus(Duration.ofMillis(782)))

        assertEquals("13s 923ms".toDuration(), Duration.ofSeconds(13).plus(Duration.ofMillis(923)))
        assertEquals("56m 13s 923ms".toDuration(), Duration.ofMinutes(56).plus(Duration.ofSeconds(13)).plus(Duration.ofMillis(923)))
        assertEquals("056m 013s 00923ms".toDuration(), Duration.ofMinutes(56).plus(Duration.ofSeconds(13)).plus(Duration.ofMillis(923)))
    }

    @Test
    fun exact() {
        assertEquals("5d".toDuration(), Duration.ofDays(5))
        assertEquals("5d 0h".toDuration(), Duration.ofDays(5))
        assertEquals("5d 0h 0m".toDuration(), Duration.ofDays(5))
        assertEquals("5d 0h 0m 0s".toDuration(), Duration.ofDays(5))
        assertEquals("5d 0h 0m 0s 0ms".toDuration(), Duration.ofDays(5))
        assertEquals("5d 0h 0m 0ms".toDuration(), Duration.ofDays(5))
        assertEquals("5d 0m 0ms".toDuration(), Duration.ofDays(5))
        assertEquals("5d 0h 0m".toDuration(), Duration.ofDays(5))

        assertEquals("12h".toDuration(), Duration.ofHours(12))
        assertEquals("12h 0m".toDuration(), Duration.ofHours(12))
        assertEquals("12h 0s".toDuration(), Duration.ofHours(12))
        assertEquals("12h 00m 0s".toDuration(), Duration.ofHours(12))
        assertEquals("12h 00m 0s 000ms".toDuration(), Duration.ofHours(12))

        assertEquals("0d".toDuration(), Duration.ofNanos(0))
        assertEquals("0d 0h".toDuration(), Duration.ofNanos(0))
        assertEquals("0d 0h 0m".toDuration(), Duration.ofNanos(0))
        assertEquals("0d 0h 0m 0s".toDuration(), Duration.ofNanos(0))
        assertEquals("0d 0h 0m 0s 0ms".toDuration(), Duration.ofNanos(0))
        assertEquals("0ms".toDuration(), Duration.ofNanos(0))
        assertEquals("0s".toDuration(), Duration.ofNanos(0))
        assertEquals("0m".toDuration(), Duration.ofNanos(0))
        assertEquals("0h".toDuration(), Duration.ofNanos(0))
    }

    @Test
    fun notValidDuration() {
        assertNull("test".toDuration())
        assertNull("16hh".toDuration())
        assertNull("123dd".toDuration())
        assertNull("435mm".toDuration())
        assertNull("923ss".toDuration())
        assertNull("34".toDuration())
        assertNull("34 d".toDuration())
        assertNull("92103 h".toDuration())
        assertNull("2109382".toDuration())
    }
}