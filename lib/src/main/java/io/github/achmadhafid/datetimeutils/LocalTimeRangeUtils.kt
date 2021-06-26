@file:Suppress("TooManyFunctions")

package io.github.achmadhafid.datetimeutils

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.FormatStyle
import java.util.Locale

//region Formatting

infix fun LocalTimeRange.withIndFormat(pattern: String): String =
    withFormat(pattern, LOCALE_IND)

infix fun LocalTimeRange.withIndFormat(style: FormatStyle): String =
    withFormat(style, LOCALE_IND)

infix fun LocalTimeRange.withFormat(pattern: String): String =
    withFormat(pattern, Locale.getDefault())

fun LocalTimeRange.withFormat(pattern: String, locale: Locale = Locale.getDefault()): String =
    "${first.withFormat(pattern, locale)} - ${second.withFormat(pattern, locale)}"

infix fun LocalTimeRange.withFormat(style: FormatStyle): String =
    withFormat(style, Locale.getDefault())

fun LocalTimeRange.withFormat(style: FormatStyle, locale: Locale = Locale.getDefault()) =
    "${first.withFormat(style, locale)} - ${second.withFormat(style, locale)}"

//endregion
//region Manipulation

inline val LocalTimeRange.flip: LocalTimeRange
    get() = second to first

inline val LocalTimeRange.sorted: LocalTimeRange
    get() = if (first.isBefore(second)) {
        first to second
    } else second to first

inline val LocalTimeRange.sortedDesc: LocalTimeRange
    get() = sorted.flip

//endregion
//region Conversion

inline val LocalTimeRange.min: LocalTime
    get() = sorted.first

inline val LocalTimeRange.max: LocalTime
    get() = sorted.second

infix fun LocalTimeRange.withDate(date: LocalDate): LocalDateTimeRange =
    LocalDateTime.of(date, first) to LocalDateTime.of(date, second)

infix fun LocalTimeRange.withDateRange(dateRange: LocalDateRange): LocalDateTimeRange =
    LocalDateTime.of(dateRange.first, first) to LocalDateTime.of(dateRange.second, second)

//endregion
//region Duration

inline val LocalTimeRange.durationInHours: Long
    get() = Duration.between(first, second).abs().toHours()

inline val LocalTimeRange.durationInMinutes: Long
    get() = Duration.between(first, second).abs().toMinutes()

inline val LocalTimeRange.durationInMillis: Long
    get() = Duration.between(first, second).abs().toMillis()

//endregion
//region Checking

inline val LocalTimeRange.isThisTime: Boolean
    get() = THIS_TIME isWithin this

inline val LocalTimeRange.isPast: Boolean
    get() = sorted.second.isPast

inline val LocalTimeRange.isFuture: Boolean
    get() = sorted.first.isFuture

infix fun LocalTimeRange.isWithin(other: LocalTimeRange): Boolean =
    first isWithin other && second isWithin other

infix fun LocalTimeRange.isWithinExclusive(other: LocalTimeRange): Boolean =
    first isWithinExclusive other && second isWithinExclusive other

infix fun LocalTimeRange.isOverlapWith(other: LocalTimeRange): Boolean =
    first isWithin other || second isWithin other
            || other.first isWithin this || other.second isWithin this

infix fun LocalTimeRange.isOverlapExclusiveWith(other: LocalTimeRange): Boolean =
    first isWithinExclusive other || second isWithinExclusive other
            || other.first isWithinExclusive this || other.second isWithinExclusive this

infix fun LocalTimeRange.equals(other: LocalTimeRange): Boolean =
    first == other.first && second == other.second

infix fun LocalTimeRange.equalsSorted(other: LocalTimeRange): Boolean =
    sorted equals other.sorted

//endregion
