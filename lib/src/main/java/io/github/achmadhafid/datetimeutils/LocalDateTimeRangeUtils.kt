@file:Suppress("TooManyFunctions")

package io.github.achmadhafid.datetimeutils

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.FormatStyle
import java.util.Locale

//region Formatting

infix fun LocalDateTimeRange.withIndFormat(pattern: String): String =
    withFormat(pattern, LOCALE_IND)

infix fun LocalDateTimeRange.withIndFormat(style: FormatStyle): String =
    withFormat(style, LOCALE_IND)

infix fun LocalDateTimeRange.withFormat(pattern: String): String =
    withFormat(pattern, Locale.getDefault())

fun LocalDateTimeRange.withFormat(pattern: String, locale: Locale): String =
    "${first.withFormat(pattern, locale)} - ${second.withFormat(pattern, locale)}"

infix fun LocalDateTimeRange.withFormat(style: FormatStyle): String =
    withFormat(style, Locale.getDefault())

fun LocalDateTimeRange.withFormat(style: FormatStyle, locale: Locale): String =
    "${first.withFormat(style, locale)} - ${second.withFormat(style, locale)}"

//endregion
//region Manipulation

inline val LocalDateTimeRange.flip: LocalDateTimeRange
    get() = second to first

inline val LocalDateTimeRange.sorted: LocalDateTimeRange
    get() = if (first.isBefore(second)) {
        first to second
    } else second to first

inline val LocalDateTimeRange.sortedDesc: LocalDateTimeRange
    get() = sorted.flip

//endregion
//region Conversion

inline val LocalDateTimeRange.min: LocalDateTime
    get() = sorted.first

inline val LocalDateTimeRange.max: LocalDateTime
    get() = sorted.second

fun LocalDateTimeRange.toLocalDateRange(): LocalDateRange =
    first.toLocalDate() to second.toLocalDate()

fun LocalDateTimeRange.toLocalTimeRange(): LocalTimeRange =
    first.toLocalTime() to second.toLocalTime()

//endregion
//region Duration

inline val LocalDateTimeRange.durationInDays: Long
    get() = Duration.between(first, second).abs().toDays()

inline val LocalDateTimeRange.durationInHours: Long
    get() = Duration.between(first, second).abs().toHours()

inline val LocalDateTimeRange.durationInMinutes: Long
    get() = Duration.between(first, second).abs().toMinutes()

inline val LocalDateTimeRange.durationInMillis: Long
    get() = Duration.between(first, second).abs().toMillis()

//endregion
//region Checking

inline val LocalDateTimeRange.isNow: Boolean
    get() = NOW isWithin this

inline val LocalDateTimeRange.isPast: Boolean
    get() = sorted.second.isPast

inline val LocalDateTimeRange.isFuture: Boolean
    get() = sorted.first.isFuture

infix fun LocalDateTimeRange.isWithin(other: LocalDateTimeRange): Boolean =
    first isWithin other && second isWithin other

infix fun LocalDateTimeRange.isWithinExclusive(other: LocalDateTimeRange): Boolean =
    first isWithinExclusive other && second isWithinExclusive other

infix fun LocalDateTimeRange.isOverlapWith(other: LocalDateTimeRange): Boolean =
    first isWithin other || second isWithin other
            || other.first isWithin this || other.second isWithin this

infix fun LocalDateTimeRange.isOverlapExclusiveWith(other: LocalDateTimeRange): Boolean =
    first isWithinExclusive other || second isWithinExclusive other
            || other.first isWithinExclusive this || other.second isWithinExclusive this

infix fun LocalDateTimeRange.equals(other: LocalDateTimeRange): Boolean =
    first == other.first && second == other.second

infix fun LocalDateTimeRange.equalsSorted(other: LocalDateTimeRange): Boolean =
    sorted equals other.sorted

//endregion
