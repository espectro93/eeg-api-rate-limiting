package com.allsmartthings.energize.domain

import java.math.BigDecimal
import java.time.YearMonth

interface EegService {
    fun getEegByYearMonthAndKwp(yearMonth: YearMonth, kwp: BigDecimal): EegValue
}