package com.allsmartthings.energize.infrastructure.clients.tsveeg

import com.allsmartthings.energize.EegService
import java.math.BigDecimal
import java.time.YearMonth

class EegFacade : EegService {

    init {
    }

    override fun getEegByYearMonthAndKwp(yearMonth: YearMonth, kwp: BigDecimal): EegType.EegByYearMonth {
        TODO("Not yet implemented")
    }
}