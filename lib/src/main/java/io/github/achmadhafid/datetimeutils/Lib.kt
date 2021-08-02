package io.github.achmadhafid.datetimeutils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.math.absoluteValue

typealias LocalTimeRange = Pair<LocalTime, LocalTime>
typealias LocalDateRange = Pair<LocalDate, LocalDate>
typealias LocalDateTimeRange = Pair<LocalDateTime, LocalDateTime>

internal inline val LOCALE_IND: Locale
    get() = Locale("in", "ID")

inline val THIS_TIME: LocalTime
    get() = LocalTime.now()
inline val THIS_HOUR: Int
    get() = THIS_TIME.hour
inline val THIS_MINUTE: Int
    get() = THIS_TIME.minute
inline val THIS_SECOND: Int
    get() = THIS_TIME.second
inline val THIS_MILLI_SECOND: Int
    get() = THIS_TIME.nano

@Suppress("MagicNumber")
inline val END_OF_DAY: LocalTime
    get() = LocalTime.MIDNIGHT.minusNanos(1)

inline val TODAY: LocalDate
    get() = LocalDate.now()
inline val TOMORROW: LocalDate
    get() = TODAY.plusDays(1)
inline val YESTERDAY: LocalDate
    get() = TODAY.minusDays(1)
inline val NEXT_WEEK: LocalDate
    get() = TODAY.plusWeeks(1)
inline val LAST_WEEK: LocalDate
    get() = TODAY.minusWeeks(1)
inline val NEXT_MONTH: LocalDate
    get() = TODAY.plusMonths(1)
inline val LAST_MONTH: LocalDate
    get() = TODAY.minusMonths(1)
inline val NEXT_YEAR: LocalDate
    get() = TODAY.plusYears(1)
inline val LAST_YEAR: LocalDate
    get() = TODAY.minusYears(1)

inline val NOW: LocalDateTime
    get() = LocalDateTime.now()

const val ONE_SECOND_IN_MILLIS = 1_000L

const val ONE_MINUTE_IN_SECONDS = 60
const val ONE_MINUTE_IN_MILLIS = ONE_MINUTE_IN_SECONDS * ONE_SECOND_IN_MILLIS

const val ONE_HOUR_IN_MINUTES = 60
const val ONE_HOUR_IN_SECONDS = ONE_HOUR_IN_MINUTES * ONE_MINUTE_IN_SECONDS
const val ONE_HOUR_IN_MILLIS = ONE_HOUR_IN_MINUTES * ONE_MINUTE_IN_MILLIS

const val ONE_DAY_IN_HOURS = 24
const val ONE_DAY_IN_MINUTES = ONE_DAY_IN_HOURS * ONE_HOUR_IN_MINUTES
const val ONE_DAY_IN_SECONDS = ONE_DAY_IN_MINUTES * ONE_MINUTE_IN_SECONDS
const val ONE_DAY_IN_MILLIS = ONE_DAY_IN_SECONDS * ONE_SECOND_IN_MILLIS

const val ONE_WEEK_IN_DAYS = 7
const val ONE_WEEK_IN_HOURS = ONE_WEEK_IN_DAYS * ONE_DAY_IN_HOURS
const val ONE_WEEK_IN_MINUTES = ONE_WEEK_IN_HOURS * ONE_HOUR_IN_MINUTES
const val ONE_WEEK_IN_SECONDS = ONE_WEEK_IN_MINUTES * ONE_MINUTE_IN_SECONDS
const val ONE_WEEK_IN_MILLIS = ONE_WEEK_IN_SECONDS * ONE_SECOND_IN_MILLIS

const val MONTHS_OF_YEAR = 12
const val DAYS_OF_WEEK = 7

internal inline val String.asFormatter: DateTimeFormatter
    get() = DateTimeFormatter.ofPattern(this)

internal inline val FormatStyle.asFormatter: DateTimeFormatter
    get() = DateTimeFormatter.ofLocalizedDateTime(this)

inline val Int.daysBack: List<LocalDate>
    get() {
        val dates = mutableListOf<LocalDate>()
        repeat(this) {
            dates.add(TODAY.minusDays(it.toLong()))
        }
        return dates
    }

inline val Int.daysForward: List<LocalDate>
    get() {
        val dates = mutableListOf<LocalDate>()
        repeat(this) {
            dates.add(TODAY.plusDays(it.toLong()))
        }
        return dates
    }

infix fun LocalDate.plusMinus(range: Int): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()

    repeat(range.absoluteValue) {
        dates.add(minusDays((it + 1).toLong()))
    }

    dates.add(this)

    repeat(range.absoluteValue) {
        dates.add(plusDays((it + 1).toLong()))
    }

    return dates
}

fun ClosedRange<LocalDate>.toList(): List<LocalDate> {
    if (start == endInclusive) return listOf(start)

    val dates = mutableListOf<LocalDate>()
    var date = start

    if (start.isBefore(endInclusive)) {
        while (date.isBefore(endInclusive.plusDays(1))) {
            dates.add(date)
            date = date.plusDays(1)
        }
    } else {
        while (date.isAfter(endInclusive.minusDays(1))) {
            dates.add(date)
            date = date.minusDays(1)
        }
    }

    return dates
}
