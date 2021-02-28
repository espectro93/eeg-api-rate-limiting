package com.allsmartthings.energize.infrastructure.clients.tsveeg

import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

interface EegTsvParser<T : EegMarker> {
    fun getYearResolutionMap(): MutableMap<Year, YearResolution>
    fun readFromTsv(): T
}

fun <T : EegMarker> EegTsvParser<T>.parseInputDate(date: String): YearMonth {
    if (isYear(date)) {
        val parsedYear = Year.parse(date)
        getYearResolutionMap().putIfAbsent(parsedYear, YearResolution.YEARLY)
        return YearMonth.of(parsedYear.value, 1)
    }
    val parsedYearMonth = parseGermanDate(date)
    getYearResolutionMap().putIfAbsent(Year.of(parsedYearMonth.year), YearResolution.MONTHLY)
    return parsedYearMonth
}

private fun parseGermanDate(date: String): YearMonth {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return YearMonth.from(LocalDate.parse(date, formatter))
}


private fun isYear(year: String): Boolean {
    return try {
        Year.parse(year);
        true
    } catch (e: DateTimeParseException) {
        false
    }
}