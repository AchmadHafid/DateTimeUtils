@file:Suppress("TooManyFunctions")
package id.co.bithealth.datetimeutils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

//region Date Formatting

infix fun LocalDate.withIndFormat(pattern: String): String =
    withFormat(pattern, LOCALE_IND)

infix fun LocalDate.withFormat(pattern: String): String =
    withFormat(pattern, Locale.getDefault())

fun LocalDate.withFormat(pattern: String, locale: Locale = Locale.getDefault()): String =
    format(DateTimeFormatter.ofPattern(pattern, locale))

infix fun LocalDate.withIndFormat(style: FormatStyle): String =
    withFormat(style, LOCALE_IND)

infix fun LocalDate.withFormat(style: FormatStyle): String =
    withFormat(style, Locale.getDefault())

fun LocalDate.withFormat(style: FormatStyle, locale: Locale = Locale.getDefault()): String =
    format(DateTimeFormatter.ofLocalizedDate(style).withLocale(locale))

//endregion
//region Date Checking

inline val TODAY: LocalDate
    get() = LocalDate.now()

inline val LocalDate.isToday
    get() = this == TODAY

inline val LocalDate.isTomorrow
    get() = minusDays(1) == TODAY

inline val LocalDate.isYesterday
    get() = plusDays(1) == TODAY

inline val LocalDate.isPast
    get() = isBefore(TODAY)

inline val LocalDate.isFuture
    get() = isAfter(TODAY)

inline val LocalDate.isWeekend
    get() = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY

inline val LocalDate.isWeekDay
    get() = !isWeekend

//endregion
//region Date Range

inline val Pair<LocalDate, LocalDate>.min: LocalDate
    get() = if (first.isBefore(second)) first else second

inline val Pair<LocalDate, LocalDate>.max: LocalDate
    get() = if (first.isBefore(second)) second else first

infix fun LocalDate.isWithin(dateRange: Pair<LocalDate, LocalDate>) =
    this == dateRange.first || this == dateRange.second || isWithinExclusive(dateRange)

infix fun LocalDate.isWithinExclusive(dateRange: Pair<LocalDate, LocalDate>) =
    isBefore(dateRange.second) && isAfter(dateRange.first)

infix fun Pair<LocalDate, LocalDate>.withIndFormat(pattern: String) =
    withFormat(pattern, LOCALE_IND)

infix fun Pair<LocalDate, LocalDate>.withFormat(pattern: String) =
    withFormat(pattern, Locale.getDefault())

fun Pair<LocalDate, LocalDate>.withFormat(pattern: String, locale: Locale = Locale.getDefault()) =
    formatDateRange(first.withFormat(pattern, locale), second.withFormat(pattern, locale))

infix fun Pair<LocalDate, LocalDate>.withIndFormat(style: FormatStyle) =
    withFormat(style, LOCALE_IND)

infix fun Pair<LocalDate, LocalDate>.withFormat(style: FormatStyle) =
    withFormat(style, Locale.getDefault())

fun Pair<LocalDate, LocalDate>.withFormat(style: FormatStyle, locale: Locale = Locale.getDefault()) =
    formatDateRange(first.withFormat(style, locale), second.withFormat(style, locale))

@Suppress("NestedBlockDepth")
private fun formatDateRange(startDate: String, endDate: String): String =
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
