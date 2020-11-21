@file:Suppress("TooManyFunctions")

package id.co.bithealth.datetimeutils

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.FormatStyle
import java.util.Locale

//region Formatting

infix fun LocalDateRange.withIndFormat(pattern: String): String =
    withFormat(pattern, LOCALE_IND)

infix fun LocalDateRange.withIndFormatUnique(pattern: String): String =
    withFormatUnique(pattern, LOCALE_IND)

infix fun LocalDateRange.withIndFormat(style: FormatStyle): String =
    withFormat(style, LOCALE_IND)

infix fun LocalDateRange.withIndFormatUnique(style: FormatStyle): String =
    withFormatUnique(style, LOCALE_IND)

infix fun LocalDateRange.withFormat(pattern: String): String =
    withFormat(pattern, Locale.getDefault())

infix fun LocalDateRange.withFormatUnique(pattern: String): String =
    withFormatUnique(pattern, Locale.getDefault())

fun LocalDateRange.withFormat(pattern: String, locale: Locale): String =
    "${first.withFormat(pattern, locale)} - ${second.withFormat(pattern, locale)}"

fun LocalDateRange.withFormatUnique(pattern: String, locale: Locale): String =
    formatUniqueDateRange(first.withFormat(pattern, locale), second.withFormat(pattern, locale))

infix fun LocalDateRange.withFormat(style: FormatStyle): String =
    withFormat(style, Locale.getDefault())

infix fun LocalDateRange.withFormatUnique(style: FormatStyle): String =
    withFormatUnique(style, Locale.getDefault())

fun LocalDateRange.withFormat(style: FormatStyle, locale: Locale): String =
    "${first.withFormat(style, locale)} - ${second.withFormat(style, locale)}"

fun LocalDateRange.withFormatUnique(style: FormatStyle, locale: Locale): String =
    formatUniqueDateRange(first.withFormat(style, locale), second.withFormat(style, locale))

@Suppress("NestedBlockDepth")
private fun formatUniqueDateRange(startDate: String, endDate: String): String =
    startDate.split(" ").let { start ->
        endDate.split(" ").let { end ->
            start.filter {
                !end.contains(it)
            }.let {
                if (it.isEmpty()) endDate
                else "${it.joinToString(" ")} - $endDate"
            }
        }
    }

//endregion
//region Manipulation

inline val LocalDateRange.flip: LocalDateRange
    get() = second to first

inline val LocalDateRange.sorted: LocalDateRange
    get() = if (first.isBefore(second)) {
        first to second
    } else second to first

inline val LocalDateRange.sortedDesc: LocalDateRange
    get() = sorted.flip

//endregion
//region Conversion

inline val LocalDateRange.min: LocalDate
    get() = sorted.first

inline val LocalDateRange.max: LocalDate
    get() = sorted.second

infix fun LocalDateRange.withTime(time: LocalTime): LocalDateTimeRange =
    LocalDateTime.of(first, time) to LocalDateTime.of(second, time)

infix fun LocalDateRange.withTimeRange(timeRange: LocalTimeRange): LocalDateTimeRange =
    LocalDateTime.of(first, timeRange.first) to LocalDateTime.of(second, timeRange.second)

//endregion
//region Duration

inline val LocalDateRange.durationInDays: Long
    get() = Duration.between(first, second).abs().toDays()

//endregion
//region Checking

inline val LocalDateRange.isToday: Boolean
    get() = TODAY isWithin this

inline val LocalDateRange.isPast: Boolean
    get() = sorted.second.isPast

inline val LocalDateRange.isFuture: Boolean
    get() = sorted.first.isFuture

infix fun LocalDateRange.isWithin(other: LocalDateRange): Boolean =
    first isWithin other && second isWithin other

infix fun LocalDateRange.isWithinExclusive(other: LocalDateRange): Boolean =
    first isWithinExclusive other && second isWithinExclusive other

infix fun LocalDateRange.isOverlapWith(other: LocalDateRange): Boolean =
    first isWithin other && second isWithin other

infix fun LocalDateRange.isOverlapExclusiveWith(other: LocalDateRange): Boolean =
    first isWithinExclusive other && second isWithinExclusive other

infix fun LocalDateRange.equals(other: LocalDateRange): Boolean =
    first == other.first && second == other.second

infix fun LocalDateRange.equalsSorted(other: LocalDateRange): Boolean =
    sorted equals other.sorted

//endregion
