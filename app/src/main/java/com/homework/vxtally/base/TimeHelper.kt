package com.homework.vxtally.base

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Range
import com.ysw.android.extensions.logE
import java.util.Date

object TimeHelper {
    @SuppressLint("SimpleDateFormat")
    fun getTimestamp(timeStr: String, pattern: String = DateAndTimePickers.PATTERN): Long {
        return SimpleDateFormat(pattern).parse(timeStr)!!.time
    }


    @SuppressLint("SimpleDateFormat")
    fun getFormatTimestamp(time: Long, pattern: String = DateAndTimePickers.PATTERN): String {
        return SimpleDateFormat(pattern).format(Date(time))
    }

    fun getMonthTimeRange(year: Int, month: Int): Range<Long> {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1, 0, 0, 0)
        val firstDayTimeMills = calendar.time.time
        calendar.roll(Calendar.DATE, -1)
        val lastDayTimeMills = calendar.time.time + 24 * 60 * 60 * 1000L
        return Range(firstDayTimeMills, lastDayTimeMills)
    }

    fun getMaxMonthDays(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1, 0, 0, 0)
        calendar.roll(Calendar.DATE, -1)
        return calendar.get(Calendar.DATE)
    }

    fun getSomeDayTimeMills(year: Int, month: Int, day: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day, 0, 0, 0)
        return calendar.timeInMillis
    }
}