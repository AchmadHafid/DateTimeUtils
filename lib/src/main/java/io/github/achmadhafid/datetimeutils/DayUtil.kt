package io.github.achmadhafid.datetimeutils

import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.Locale

inline val Locale.firstDayOfWeek: DayOfWeek
    get() = WeekFields.of(this).firstDayOfWeek
