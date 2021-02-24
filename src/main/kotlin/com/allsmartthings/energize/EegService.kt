package com.allsmartthings.energize

import com.allsmartthings.energize.infrastructure.clients.tsveeg.EegType
import java.math.BigDecimal
import java.time.YearMonth

interface EegService {
    fun getEegByYearMonthAndKwp(yearMonth: YearMonth, kwp: BigDecimal): EegType.EegByYearMonth
}