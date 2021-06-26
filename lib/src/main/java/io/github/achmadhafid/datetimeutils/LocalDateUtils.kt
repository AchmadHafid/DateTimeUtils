@file:Suppress("TooManyFunctions")

package io.github.achmadhafid.datetimeutils

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

//region Parsing

fun String.toLocalDate(): LocalDate =
    LocalDate.parse(this)

fun String.toLocalDateOrToday(): LocalDate =
    toLocalDateOrDefault(TODAY)

infix fun String.toLocalDateOrDefault(localDate: LocalDate): LocalDate =
    runCatching { LocalDate.parse(this) }.getOrDefault(localDate)

infix fun String.toLocalDate(pattern: String): LocalDate =
    LocalDate.parse(this, pattern.asFormatter)

infix fun String.toLocalDateOrToday(pattern: String): LocalDate =
    toLocalDateOrDefault(pattern, TODAY)

fun String.toLocalDateOrDefault(pattern: String, localDate: LocalDate): LocalDate =
    runCatching { LocalDate.parse(this, pattern.asFormatter) }.getOrDefault(localDate)

fun String.toLocalDate(pattern: String, locale: Locale): LocalDate =
    LocalDate.parse(this, pattern.asFormatter.withLocale(locale))

fun String.toLocalDateOrToday(pattern: String, locale: Locale): LocalDate =
    toLocalDateOrDefault(pattern, locale, TODAY)

fun String.toLocalDateOrDefault(pattern: String, locale: Locale, localDate: LocalDate): LocalDate =
    runCatching { LocalDate.parse(this, pattern.asFormatter.withLocale(locale)) }.getOrDefault(
        localDate
    )

infix fun String.toLocalDate(style: FormatStyle): LocalDate =
    LocalDate.parse(this, style.asFormatter)

infix fun String.toLocalDateOrToday(style: FormatStyle): LocalDate =
    toLocalDateOrDefault(style, TODAY)

fun String.toLocalDateOrDefault(style: FormatStyle, localDate: LocalDate): LocalDate =
    runCatching { LocalDate.parse(this, style.asFormatter) }.getOrDefault(localDate)

fun String.toLocalDate(style: FormatStyle, locale: Locale): LocalDate =
    LocalDate.parse(this, style.asFormatter.withLocale(locale))

fun String.toLocalDateOrToday(style: FormatStyle, locale: Locale): LocalDate =
    toLocalDateOrDefault(style, locale, TODAY)

fun String.toLocalDateOrDefault(
    style: FormatStyle,
    locale: Locale,
    localDate: LocalDate
): LocalDate =
    runCatching { LocalDate.parse(this, style.asFormatter.withLocale(locale)) }.getOrDefault(
        localDate
    )

//endregion
//region Formatting

infix fun LocalDate.withIndFormat(pattern: String): String =
    withFormat(pattern, LOCALE_IND)

infix fun LocalDate.withIndFormat(style: FormatStyle): String =
    withFormat(style, LOCALE_IND)

infix fun LocalDate.withFormat(pattern: String): String =
    withFormat(pattern, Locale.getDefault())

fun LocalDate.withFormat(pattern: String, locale: Locale = Locale.getDefault()): String =
    format(DateTimeFormatter.ofPattern(pattern, locale))

infix fun LocalDate.withFormat(style: FormatStyle): String =
    withFormat(style, Locale.getDefault())

fun LocalDate.withFormat(style: FormatStyle, locale: Locale = Locale.getDefault()): String =
    format(DateTimeFormatter.ofLocalizedDate(style).withLocale(locale))

//endregion
//region Manipulation

inline val LocalDate.toStartOfTheMonth: LocalDate
    get() = withDayOfMonth(1)
inline val LocalDate.toEndOfTheMonth: LocalDate
    get() = plusMonths(1).toStartOfTheMonth.minusDays(1)
inline val LocalDate.toStartOfTheYear: LocalDate
    get() = withDayOfYear(1)
inline val LocalDate.toEndOfTheYear: LocalDate
    get() = plusYears(1).toStartOfTheYear.minusDays(1)

//endregion
//region Conversion

fun Instant.toLocalDate(zoneId: ZoneId = ZoneId.systemDefault()): LocalDate =
    toLocalDateTime(zoneId).toLocalDate()

fun LocalDate.toInstantRange(zoneId: ZoneId = ZoneId.systemDefault()): Pair<Instant, Instant> =
    ZonedDateTime.of(atStartOfDay(), zoneId).toInstant() to
            ZonedDateTime.of(atStartOfDay().plusDays(1), zoneId).toInstant()

infix fun LocalDate.withTime(localTime: LocalTime): LocalDateTime =
    LocalDateTime.of(this, localTime)

fun LocalDate.sinceYesterday(): LocalDateRange =
    minusDays(1) to this

fun LocalDate.sincePreviousWeek(): LocalDateRange =
    this to minusDays(DAYS_OF_WEEK.toLong())

fun LocalDate.sincePreviousMonth(): LocalDateRange =
    this to minusMonths(1)

fun LocalDate.sincePreviousYear(): LocalDateRange =
    this to minusYears(1)

fun LocalDate.untilTomorrow(): LocalDateRange =
    this to plusDays(1)

fun LocalDate.untilNextWeek(): LocalDateRange =
    this to plusDays(DAYS_OF_WEEK.toLong())

fun LocalDate.untilNextMonth(): LocalDateRange =
    this to plusMonths(1)

fun LocalDate.untilNextYear(): LocalDateRange =
    this to plusYears(1)

//endregion
//region Checking

inline val LocalDate.isToday: Boolean
    get() = equals(TODAY)

inline val LocalDate.isPast: Boolean
    get() = isBefore(TODAY)

inline val LocalDate.isFuture: Boolean
    get() = isAfter(TODAY)

inline val LocalDate.isTomorrow: Boolean
    get() = minusDays(1) == TODAY

inline val LocalDate.isYesterday: Boolean
    get() = plusDays(1) == TODAY

inline val LocalDate.isWeekend: Boolean
    get() = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY

inline val LocalDate.isWeekDay: Boolean
    get() = !isWeekend

infix fun LocalDate.isWithin(dateRange: LocalDateRange): Boolean =
    this == dateRange.first || this == dateRange.second || isWithinExclusive(dateRange)

infix fun LocalDate.isWithinExclusive(dateRange: LocalDateRange): Boolean =
    isBefore(dateRange.second) && isAfter(dateRange.first)

//endregion
