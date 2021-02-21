package com.allsmartthings.energize

import java.math.BigDecimal
import java.time.Year
import java.time.YearMonth

data class EegByYear(
        val date: Year,
        val rangeValues: List<EegForKwpRange>
)

data class EegByYearMonth(
        val date: YearMonth,
        val rangeValues: List<EegForKwpRange>
)

data class EegForKwpRange(
        val value: BigDecimal,
        val lowerKwpBound: Int,
        val upperKwpBound: Int?
)

data class OpenSpaceSystemEeg(
        val date: YearMonth,
        val value: BigDecimal,
        val upperKwpBound: Int?
)

data class EegFacadeEnclosure(
        val date: YearMonth,
        val value: BigDecimal
)

data class EegWithSelfConsumptionByYear(
        val date: Year,
        val rangeValues: List<EegForKwpRange>,
        val aboveThreshold: Boolean
)

data class EegWithSelfConsumptionByYearMonth(
        val date: YearMonth,
        val rangeValues: List<EegForKwpRange>,
        val aboveThreshold: Boolean
)