package io.github.achmadhafid.datetimeutils

import java.time.Instant

inline val EpochSecondNow: Int
    get() = Instant.now().epochSecond.toInt()

inline val EpochMillisNow: Long
    get() = Instant.now().toEpochMilli()
