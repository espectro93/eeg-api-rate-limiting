package com.allsmartthings.energize

import java.math.BigDecimal
import java.time.YearMonth

class EegFacade : EegService {
    private val eegList: List<Eeg>
    private val openSpaceSystemEegList: List<OpenSpaceSystemEeg>
    private val eegFacadeEnclosureList: List<EegFacadeEnclosure>

    init {
        eegList = ArrayList()
        openSpaceSystemEegList = ArrayList()
        eegFacadeEnclosureList = ArrayList()
    }

    override fun getEegByYearMonthAndKwp(yearMonth: YearMonth, kwp: BigDecimal): Eeg {
        TODO("Not yet implemented")
    }
}