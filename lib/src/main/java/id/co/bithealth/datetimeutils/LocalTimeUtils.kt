@file:Suppress("TooManyFunctions")
package id.co.bithealth.datetimeutils

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

//region Time Formatting

infix fun LocalTime.withIndFormat(pattern: String): String =
    withFormat(pattern, LOCALE_IND)

infix fun LocalTime.withFormat(pattern: String): String =
    withFormat(pattern, Locale.getDefault())

fun LocalTime.withFormat(pattern: String, locale: Locale = Locale.getDefault()): String =
    format(DateTimeFormatter.ofPattern(pattern, locale))

infix fun LocalTime.withFormat(style: FormatStyle): String =
    withFormat(style, Locale.getDefault())

infix fun LocalTime.withIndFormat(style: FormatStyle): String =
    withFormat(style, LOCALE_IND)

fun LocalTime.withFormat(style: FormatStyle, locale: Locale = Locale.getDefault()): String =
    format(DateTimeFormatter.ofLocalizedTime(style).withLocale(locale))

//endregion
//region Time Manipulation

inline val LocalTime.removeSecondsAndNanos: LocalTime
    get() = removeSeconds.removeNanos

inline val LocalTime.removeSeconds: LocalTime
    get() = withSecond(0)

inline val LocalTime.removeNanos: LocalTime
    get() = withNano(0)

//endregion
//region Time Checking

inline val THIS_TIME: LocalTime
    get() = LocalTime.now()

fun LocalTime.isNow(ignoreSeconds: Boolean = true, ignoreMillis: Boolean = true) =
    apply {
        if (ignoreSeconds) removeSeconds
        if (ignoreMillis) removeNanos
    } == THIS_TIME.apply {
        if (ignoreSeconds) removeSeconds
        if (ignoreMillis) removeNanos
    }

inline val LocalTime.isPast
    get() = isBefore(THIS_TIME)

inline val LocalTime.isFuture
    get() = isAfter(THIS_TIME)

//endregion
//region Time Range

inline val Pair<LocalTime, LocalTime>.min: LocalTime
    get() = if (first.isBefore(second)) first else second

inline val Pair<LocalTime, LocalTime>.max: LocalTime
    get() = if (first.isBefore(second)) second else first

infix fun LocalTime.isWithin(timeRange: Pair<LocalTime, LocalTime>) =
    this == timeRange.first || this == timeRange.second || isWithinExclusive(timeRange)

infix fun LocalTime.isWithinExclusive(timeRange: Pair<LocalTime, LocalTime>) =
    isBefore(timeRange.second) && isAfter(timeRange.first)

infix fun Pair<LocalTime, LocalTime>.withIndFormat(pattern: String) =
    withFormat(pattern, LOCALE_IND)

infix fun Pair<LocalTime, LocalTime>.withFormat(pattern: String) =
    withFormat(pattern, Locale.getDefault())

fun Pair<LocalTime, LocalTime>.withFormat(pattern: String, locale: Locale = Locale.getDefault()) =
    "${first.withFormat(pattern, locale)} - ${second.withFormat(pattern, locale)}"

infix fun Pair<LocalTime, LocalTime>.withIndFormat(style: FormatStyle) =
    withFormat(style, LOCALE_IND)

infix fun Pair<LocalTime, LocalTime>.withFormat(style: FormatStyle) =
    withFormat(style, Locale.getDefault())

fun Pair<LocalTime, LocalTime>.withFormat(style: FormatStyle, locale: Locale = Locale.getDefault()) =
    "${first.withFormat(style, locale)} - ${second.withFormat(style, locale)}"

//endregion
