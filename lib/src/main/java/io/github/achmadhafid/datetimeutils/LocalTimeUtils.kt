@file:Suppress("TooManyFunctions")

package io.github.achmadhafid.datetimeutils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

//region Parsing

fun String.toLocalTime(): LocalTime =
    LocalTime.parse(this)

fun String.toLocalTimeOrThisTime(): LocalTime =
    toLocalTimeOrDefault(THIS_TIME)

infix fun String.toLocalTimeOrDefault(localTime: LocalTime): LocalTime =
    runCatching { LocalTime.parse(this) }.getOrDefault(localTime)

infix fun String.toLocalTime(pattern: String): LocalTime =
    LocalTime.parse(this, pattern.asFormatter)

infix fun String.toLocalTimeOrThisTime(pattern: String): LocalTime =
    toLocalTimeOrDefault(pattern, THIS_TIME)

fun String.toLocalTimeOrDefault(pattern: String, localTime: LocalTime): LocalTime =
    runCatching { LocalTime.parse(this, pattern.asFormatter) }.getOrDefault(localTime)

fun String.toLocalTime(pattern: String, locale: Locale): LocalTime =
    LocalTime.parse(this, pattern.asFormatter.withLocale(locale))

fun String.toLocalTimeOrThisTime(pattern: String, locale: Locale): LocalTime =
    toLocalTimeOrDefault(pattern, locale, THIS_TIME)

fun String.toLocalTimeOrDefault(pattern: String, locale: Locale, localTime: LocalTime): LocalTime =
    runCatching { LocalTime.parse(this, pattern.asFormatter.withLocale(locale)) }.getOrDefault(localTime)

infix fun String.toLocalTime(style: FormatStyle): LocalTime =
    LocalTime.parse(this, style.asFormatter)

infix fun String.toLocalTimeOrThisTime(style: FormatStyle): LocalTime =
    toLocalTimeOrDefault(style, THIS_TIME)

fun String.toLocalTimeOrDefault(style: FormatStyle, localTime: LocalTime): LocalTime =
    runCatching { LocalTime.parse(this, style.asFormatter) }.getOrDefault(localTime)

fun String.toLocalTime(style: FormatStyle, locale: Locale): LocalTime =
    LocalTime.parse(this, style.asFormatter.withLocale(locale))

fun String.toLocalTimeOrThisTime(style: FormatStyle, locale: Locale): LocalTime =
    toLocalTimeOrDefault(style, locale, THIS_TIME)

fun String.toLocalTimeOrDefault(style: FormatStyle, locale: Locale, localTime: LocalTime): LocalTime =
    runCatching { LocalTime.parse(this, style.asFormatter.withLocale(locale)) }.getOrDefault(localTime)

//endregion
//region Formatting

infix fun LocalTime.withIndFormat(pattern: String): String =
    withFormat(pattern, LOCALE_IND)

infix fun LocalTime.withIndFormat(style: FormatStyle): String =
    withFormat(style, LOCALE_IND)

infix fun LocalTime.withFormat(pattern: String): String =
    withFormat(pattern, Locale.getDefault())

fun LocalTime.withFormat(pattern: String, locale: Locale = Locale.getDefault()): String =
    format(DateTimeFormatter.ofPattern(pattern, locale))

infix fun LocalTime.withFormat(style: FormatStyle): String =
    withFormat(style, Locale.getDefault())

fun LocalTime.withFormat(style: FormatStyle, locale: Locale = Locale.getDefault()): String =
    format(DateTimeFormatter.ofLocalizedTime(style).withLocale(locale))

//endregion
//region Manipulation

inline val LocalTime.resetSecondsAndNanos: LocalTime
    get() = resetSeconds.resetNanos

inline val LocalTime.resetSeconds: LocalTime
    get() = withSecond(0)

inline val LocalTime.resetNanos: LocalTime
    get() = withNano(0)

//endregion
//region Conversion

infix fun LocalTime.withDate(localDate: LocalDate): LocalDateTime =
    LocalDateTime.of(localDate, this)

fun LocalTime.sinceStartOfTheDay(): LocalTimeRange =
    LocalTime.MIDNIGHT to this

fun LocalTime.untilEndOfTheDay(): LocalTimeRange =
    this to END_OF_DAY

//endregion
//region Checking

inline val LocalTime.isThisTime: Boolean
    get() = equals(THIS_TIME)

inline val LocalTime.isPast: Boolean
    get() = isBefore(THIS_TIME)

inline val LocalTime.isFuture: Boolean
    get() = isAfter(THIS_TIME)

infix fun LocalTime.isWithin(timeRange: LocalTimeRange): Boolean =
    this == timeRange.first || this == timeRange.second || isWithinExclusive(timeRange)

infix fun LocalTime.isWithinExclusive(timeRange: LocalTimeRange): Boolean =
    isBefore(timeRange.sorted.second) && isAfter(timeRange.sorted.first)

//endregion
