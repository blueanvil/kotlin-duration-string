package com.blueanvil

import java.time.Duration

/**
 * @author Cosmin Marginean
 */
fun Duration.toHumanReadableString(): String {
    val nanoValue = this.toNanos()
    if (nanoValue == 0L) {
        return "0ms"
    }

    val days = nanoValue / NANO_DAY
    var remainderD = nanoValue - days * NANO_DAY

    val hours = remainderD / NANO_HOUR
    var remainderH = remainderD - hours * NANO_HOUR

    val minutes = remainderH / NANO_MIN
    val remainderM = remainderH - minutes * NANO_MIN

    val seconds = remainderM / NANO_SEC
    val remainderS = remainderM - seconds * NANO_SEC

    val milliseconds = remainderS / NANO_MS
    val elements = listOf("${days}d", "${hours}h", "${minutes}m", "${seconds}s", "${milliseconds}ms")
    val start = elements.indexOfFirst { it[0] != '0' }
    val end = elements.indexOfLast { it[0] != '0' }
    return elements.subList(start, end + 1).joinToString(" ")

//    return "${days}d ${hours}h ${minutes}m ${seconds}s ${milliseconds}ms"
//            .replace("\\s0ms".toRegex(), "")
//            .replace("\\s0s".toRegex(), "")
//            .replace("\\s0m".toRegex(), "")
//            .replace("\\s0h".toRegex(), "")
//            .replace("^0d".toRegex(), "")
//            .trim()
}