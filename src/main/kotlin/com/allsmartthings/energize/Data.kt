package com.allsmartthings.energize

import java.math.BigDecimal
import java.time.Year
import java.time.YearMonth

sealed class EegType {
    data class EegByYear(val date: Year, val rangeValues: List<EegForKwpRange>) : EegType()
    data class EegByYearMonth(val date: YearMonth, val rangeValues: List<EegForKwpRange>) : EegType()
    data class OpenSpaceSystemEeg(val date: YearMonth, val value: BigDecimal, val upperKwpBound: Int?) : EegType()
    data class EegFacadeEnclosure(val date: YearMonth,val value: BigDecimal) : EegType()
    data class SystemOnSealedOrConversionAreaEeg(val date: YearMonth, val value: BigDecimal): EegType()
    data class SystemOnAcreEeg(val date: YearMonth, val value: BigDecimal)
    data class EegWithSelfConsumptionByYear(val date: Year,val rangeValues: List<EegForKwpRange>,val aboveThreshold: Boolean): EegType()
    data class EegWithSelfConsumptionByYearMonth(val date: YearMonth, val rangeValues: List<EegForKwpRange>, val aboveThreshold: Boolean): EegType()
}

data class EegForKwpRange(
    val value: BigDecimal,
    val lowerKwpBound: Int,
    val upperKwpBound: Int?
)