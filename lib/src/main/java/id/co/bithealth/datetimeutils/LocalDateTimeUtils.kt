@file:Suppress("TooManyFunctions")

package id.co.bithealth.datetimeutils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

//region Date Time Formatting

infix fun LocalDateTime.withIndFormat(pattern: String): String =
    withFormat(pattern, LOCALE_IND)

infix fun LocalDateTime.withFormat(pattern: String): String =
    withFormat(pattern, Locale.getDefault())

fun LocalDateTime.withFormat(pattern: String, locale: Locale = Locale.getDefault()): String =
    format(DateTimeFormatter.ofPattern(pattern, locale))

infix fun LocalDateTime.withIndFormat(style: FormatStyle): String =
    withFormat(style, LOCALE_IND)

infix fun LocalDateTime.withFormat(style: FormatStyle): String =
    withFormat(style, Locale.getDefault())

fun LocalDateTime.withFormat(style: FormatStyle, locale: Locale = Locale.getDefault()): String =
    format(DateTimeFormatter.ofLocalizedDateTime(style).withLocale(locale))

//endregion
//region Date Time Manipulation

inline val Long.toLocalDateTime: LocalDateTime
    get() = Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

infix fun Long.toLocalDateTime(zoneId: ZoneId): LocalDateTime =
    Instant.ofEpochMilli(this)
        .atZone(zoneId)
        .toLocalDateTime()

inline val LocalDateTime.toStartOfTheDay: LocalDateTime
    get() = toLocalDate().atStartOfDay()

inline val LocalDateTime.toEndOfTheDay: LocalDateTime
    get() = LocalDateTime.of(toLocalDate(), LocalTime.of(23, 59, 59))

//endregion
//region Date Time Checking

inline val NOW: LocalDateTime
    get() = LocalDateTime.now()

inline val LocalDateTime.isPast
    get() = isBefore(LocalDateTime.now())

inline val LocalDateTime.future
    get() = isAfter(LocalDateTime.now())

//endregion
//region Date Time Range

inline val Pair<LocalDateTime, LocalDateTime>.min: LocalDateTime
    get() = if (first.isBefore(second)) first else second

inline val Pair<LocalDateTime, LocalDateTime>.max: LocalDateTime
    get() = if (first.isBefore(second)) second else first

infix fun LocalDateTime.isWithin(dateTimeRange: Pair<LocalDateTime, LocalDateTime>) =
    isEqual(dateTimeRange.first) || isEqual(dateTimeRange.second) || isWithinExclusive(dateTimeRange)

infix fun LocalDateTime.isWithinExclusive(dateTimeRange: Pair<LocalDateTime, LocalDateTime>) =
    isBefore(dateTimeRange.second) && isAfter(dateTimeRange.first)

fun Pair<LocalDateTime, LocalDateTime>.withIndFormat(datePattern: String, timePattern: String) =
    withFormat(datePattern, timePattern, LOCALE_IND)

fun Pair<LocalDateTime, LocalDateTime>.withFormat(
    datePattern: String,
    timePattern: String,
    locale: Locale = Locale.getDefault()
) = (toLocalDateRange.withFormat(datePattern, locale)) to (toLocalTimeRange.withFormat(timePattern, locale))

fun Pair<LocalDateTime, LocalDateTime>.withIndFormat(datePattern: String, timeStyle: FormatStyle) =
    withFormat(datePattern, timeStyle, LOCALE_IND)

fun Pair<LocalDateTime, LocalDateTime>.withFormat(
    datePattern: String,
    timeStyle: FormatStyle,
    locale: Locale = Locale.getDefault()
) = (toLocalDateRange.withFormat(datePattern, locale)) to (toLocalTimeRange.withFormat(timeStyle, locale))

fun Pair<LocalDateTime, LocalDateTime>.withIndFormat(dateStyle: FormatStyle, timePattern: String) =
    withFormat(dateStyle, timePattern, LOCALE_IND)

fun Pair<LocalDateTime, LocalDateTime>.withFormat(
    dateStyle: FormatStyle,
    timePattern: String,
    locale: Locale = Locale.getDefault()
) = (toLocalDateRange.withFormat(dateStyle, locale)) to (toLocalTimeRange.withFormat(timePattern, locale))

fun Pair<LocalDateTime, LocalDateTime>.withIndFormat(dateStyle: FormatStyle, timeStyle: FormatStyle) =
    withFormat(dateStyle, timeStyle, LOCALE_IND)

fun Pair<LocalDateTime, LocalDateTime>.withFormat(
    dateStyle: FormatStyle,
    timeStyle: FormatStyle,
    locale: Locale = Locale.getDefault()
) = (toLocalDateRange.withFormat(dateStyle, locale)) to (toLocalTimeRange.withFormat(timeStyle, locale))

inline val Pair<LocalDateTime, LocalDateTime>.toLocalDateRange: Pair<LocalDate, LocalDate>
    get() = first.toLocalDate() to second.toLocalDate()

inline val Pair<LocalDateTime, LocalDateTime>.toLocalTimeRange: Pair<LocalTime, LocalTime>
    get() = first.toLocalTime() to second.toLocalTime()

//endregion
