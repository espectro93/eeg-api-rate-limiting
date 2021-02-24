package com.allsmartthings.energize.infrastructure.clients.tsveeg

import java.math.BigDecimal
import java.time.YearMonth

sealed class EegType {
    data class EegByYearMonth(val date: YearMonth, val rangeValues: List<EegForKwpRange>) : EegType()
    data class OpenSpaceSystemEeg(val date: YearMonth, val value: BigDecimal, val upperKwpBound: Int?) : EegType()
    data class EegFacadeEnclosure(val date: YearMonth,val value: BigDecimal) : EegType()
    data class SystemOnSealedOrConversionAreaEeg(val date: YearMonth, val value: BigDecimal): EegType()
    data class SystemOnAcreEeg(val date: YearMonth, val value: BigDecimal)
    data class EegWithSelfConsumptionByYearMonth(val date: YearMonth, val rangeValues: List<EegForKwpRange>, val aboveThreshold: Boolean): EegType()
}

data class EegForKwpRange(
    val value: BigDecimal,
    val lowerKwpBound: Int,
    val upperKwpBound: Int?
)

interface Eeg{}