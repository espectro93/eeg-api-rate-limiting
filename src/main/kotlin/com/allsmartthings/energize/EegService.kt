package com.allsmartthings.energize

import java.math.BigDecimal
import java.time.YearMonth

interface EegService {
    fun getEegByYearMonthAndKwp(yearMonth: YearMonth, kwp: BigDecimal): Eeg
}