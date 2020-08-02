package com.blueanvil

import java.time.Duration

/**
 * @author Cosmin Marginean
 */

internal val REGEX_DURATION = """
    ^(
       (
         ((?<days>\d+)d)?(\s*)
         ((?<hours>(0*(2[0-3]|[0-1]?[0-9])))h)?(\s*)
         ((?<minutes>(0*([0-5]?[0-9])))m)?(\s*)
         ((?<seconds>(0*([0-5]?[0-9])))s)?(\s*)
         ((?<milliseconds>(0*([0-9]|[1-9][0-9]|[1-9][0-9][0-9])))ms)?
      )
    | (
         ((?<days2>\d+)d)
        |((?<hours2>\d+)h)
        |((?<minutes2>\d+)m)
        |((?<seconds2>\d+)s)
        |((?<milliseconds2>\d+)ms)
      )
    )$
""".replace("\\s+".toRegex(), "")
        .trim()
        .toRegex()

internal const val NANO_MS = 1000000L
internal const val NANO_SEC = 1000 * NANO_MS
internal const val NANO_MIN = 60 * NANO_SEC
internal const val NANO_HOUR = 60 * NANO_MIN
internal const val NANO_DAY = 24 * NANO_HOUR

internal val GROUP_UNIT_CONVERTERS = mapOf(
        "days" to NANO_DAY,
        "hours" to NANO_HOUR,
        "minutes" to NANO_MIN,
        "seconds" to NANO_SEC,
        "milliseconds" to NANO_MS,
        "days2" to NANO_DAY,
        "hours2" to NANO_HOUR,
        "minutes2" to NANO_MIN,
        "seconds2" to NANO_SEC,
        "milliseconds2" to NANO_MS
)

fun String.toDuration(): Duration? {
    val matcher = REGEX_DURATION.find(this.trim()) ?: return null
    var durationNs = 0L

    GROUP_UNIT_CONVERTERS.forEach { (groupName, multiplier) ->
        val amount = matcher.groups[groupName]?.value?.toLong()
        if (amount != null)
            durationNs += amount * multiplier
    }
    return Duration.ofNanos(durationNs)
}
