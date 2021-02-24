package com.allsmartthings.energize.infrastructure.clients.tsveeg

import com.allsmartthings.energize.YearResolution
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeParseException

interface EegTsvParser<T : Eeg> {
    fun getYearResolutionMap(): MutableMap<Year, YearResolution>
    fun getEeg(): T
}

fun <T: Eeg> EegTsvParser<T>.parseInputDate(date: String): YearMonth {
    if (isYear(date)) {
        val parsedYear = Year.parse(date)
        getYearResolutionMap().putIfAbsent(parsedYear, YearResolution.YEARLY)
        return YearMonth.of(parsedYear.value, 1)
    }
    val parsedYearMonth = YearMonth.from(LocalDate.parse(date))
    val parsedYear = Year.of(parsedYearMonth.year)
    getYearResolutionMap().putIfAbsent(parsedYear, YearResolution.MONTHLY)
    return parsedYearMonth
}

private fun isYear(year: String): Boolean {
    return try {
        Year.parse(year);
        true
    } catch (e: DateTimeParseException) {
        false
    }
}