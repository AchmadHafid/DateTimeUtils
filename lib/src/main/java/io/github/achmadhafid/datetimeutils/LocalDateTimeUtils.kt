@file:Suppress("TooManyFunctions")

package io.github.achmadhafid.datetimeutils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

//region Parsing
//region Timestamp

fun Long.toLocalDateTime(): LocalDateTime =
    Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

infix fun Long.toLocalDateTime(zoneId: ZoneId): LocalDateTime =
    Instant.ofEpochMilli(this)
        .atZone(zoneId)
        .toLocalDateTime()

fun Long.toLocalDateTimeOrNow(): LocalDateTime =
    toLocalDateTimeOrDefault(NOW)

infix fun Long.toLocalDateTimeOrNow(zoneId: ZoneId): LocalDateTime =
    toLocalDateTimeOrDefault(zoneId, NOW)

infix fun Long.toLocalDateTimeOrDefault(dateTime: LocalDateTime): LocalDateTime =
    runCatching {
        Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }.getOrDefault(dateTime)

fun Long.toLocalDateTimeOrDefault(zoneId: ZoneId, dateTime: LocalDateTime): LocalDateTime =
    runCatching {
        Instant.ofEpochMilli(this)
            .atZone(zoneId)
            .toLocalDateTime()
    }.getOrDefault(dateTime)

//endregion
//region Date Time String

fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this)

fun String.toLocalDateTimeOrNow(): LocalDateTime =
    toLocalDateTimeOrDefault(NOW)

infix fun String.toLocalDateTimeOrDefault(dateTime: LocalDateTime): LocalDateTime =
    runCatching { LocalDateTime.parse(this) }.getOrDefault(dateTime)

infix fun String.toLocalDateTime(pattern: String): LocalDateTime =
    LocalDateTime.parse(this, pattern.asFormatter)

infix fun String.toLocalDateTimeOrNow(pattern: String): LocalDateTime =
    toLocalDateTimeOrDefault(pattern, NOW)

fun String.toLocalDateTimeOrDefault(pattern: String, dateTime: LocalDateTime): LocalDateTime =
    runCatching { LocalDateTime.parse(this, pattern.asFormatter) }.getOrDefault(dateTime)

fun String.toLocalDateTime(pattern: String, locale: Locale): LocalDateTime =
    LocalDateTime.parse(this, pattern.asFormatter.withLocale(locale))

fun String.toLocalDateTimeOrNow(pattern: String, locale: Locale): LocalDateTime =
    toLocalDateTimeOrDefault(pattern, locale, NOW)

fun String.toLocalDateTimeOrDefault(
    pattern: String,
    locale: Locale,
    dateTime: LocalDateTime
): LocalDateTime =
    runCatching { LocalDateTime.parse(this, pattern.asFormatter.withLocale(locale)) }.getOrDefault(
        dateTime
    )

infix fun String.toLocalDateTime(style: FormatStyle): LocalDateTime =
    LocalDateTime.parse(this, style.asFormatter)

infix fun String.toLocalDateTimeOrNow(style: FormatStyle): LocalDateTime =
    toLocalDateTimeOrDefault(style, NOW)

fun String.toLocalDateTimeOrDefault(style: FormatStyle, dateTime: LocalDateTime): LocalDateTime =
    runCatching { LocalDateTime.parse(this, style.asFormatter) }.getOrDefault(dateTime)

fun String.toLocalDateTime(style: FormatStyle, locale: Locale): LocalDateTime =
    LocalDateTime.parse(this, style.asFormatter.withLocale(locale))

fun String.toLocalDateTimeOrNow(style: FormatStyle, locale: Locale): LocalDateTime =
    toLocalDateTimeOrDefault(style, locale, NOW)

fun String.toLocalDateTimeOrDefault(
    style: FormatStyle,
    locale: Locale,
    dateTime: LocalDateTime
): LocalDateTime =
    runCatching { LocalDateTime.parse(this, style.asFormatter.withLocale(locale)) }.getOrDefault(
        dateTime
    )

//endregion
//endregion
//region Formatting

infix fun LocalDateTime.withIndFormat(pattern: String): String =
    withFormat(pattern, LOCALE_IND)

infix fun LocalDateTime.withIndFormat(style: FormatStyle): String =
    withFormat(style, LOCALE_IND)

infix fun LocalDateTime.withFormat(pattern: String): String =
    withFormat(pattern, Locale.getDefault())

fun LocalDateTime.withFormat(pattern: String, locale: Locale = Locale.getDefault()): String =
    format(DateTimeFormatter.ofPattern(pattern, locale))

infix fun LocalDateTime.withFormat(style: FormatStyle): String =
    withFormat(style, Locale.getDefault())

fun LocalDateTime.withFormat(style: FormatStyle, locale: Locale = Locale.getDefault()): String =
    format(DateTimeFormatter.ofLocalizedDateTime(style).withLocale(locale))

//endregion
//region Manipulation

inline val LocalDateTime.toStartOfTheDay: LocalDateTime
    get() = toLocalDate().atStartOfDay()

inline val LocalDateTime.toEndOfTheDay: LocalDateTime
    get() = LocalDateTime.of(toLocalDate(), END_OF_DAY)

//endregion
//region Conversion

fun Instant.toLocalDateTime(zoneId: ZoneId = ZoneId.systemDefault()): LocalDateTime =
    LocalDateTime.ofInstant(this, zoneId)

fun LocalDateTime.toInstant(zoneId: ZoneId = ZoneId.systemDefault()): Instant =
    ZonedDateTime.of(this, zoneId).toInstant()

fun LocalDateTime.sinceStartOfTheDay(): LocalDateTimeRange =
    toStartOfTheDay to this

fun LocalDateTime.untilEndOfTheDay(): LocalDateTimeRange =
    this to toEndOfTheDay

fun LocalDateTime.sinceYesterday(): LocalDateTimeRange =
    minusDays(1) to this

fun LocalDateTime.sincePreviousWeek(): LocalDateTimeRange =
    this to minusDays(DAYS_OF_WEEK.toLong())

fun LocalDateTime.sincePreviousMonth(): LocalDateTimeRange =
    this to minusMonths(1)

fun LocalDateTime.sincePreviousYear(): LocalDateTimeRange =
    this to minusYears(1)

fun LocalDateTime.untilTomorrow(): LocalDateTimeRange =
    this to plusDays(1)

fun LocalDateTime.untilNextWeek(): LocalDateTimeRange =
    this to plusDays(DAYS_OF_WEEK.toLong())

fun LocalDateTime.untilNextMonth(): LocalDateTimeRange =
    this to plusMonths(1)

fun LocalDateTime.untilNextYear(): LocalDateTimeRange =
    this to plusYears(1)

//endregion
//region Checking

inline val LocalDateTime.isNow: Boolean
    get() = equals(NOW)

inline val LocalDateTime.isPast: Boolean
    get() = isBefore(LocalDateTime.now())

inline val LocalDateTime.isFuture: Boolean
    get() = isAfter(LocalDateTime.now())

infix fun LocalDateTime.isWithin(dateTimeRange: LocalDateTimeRange): Boolean =
    isEqual(dateTimeRange.first) || isEqual(dateTimeRange.second) || isWithinExclusive(dateTimeRange)

infix fun LocalDateTime.isWithinExclusive(dateTimeRange: LocalDateTimeRange): Boolean =
    isBefore(dateTimeRange.second) && isAfter(dateTimeRange.first)

//endregion
