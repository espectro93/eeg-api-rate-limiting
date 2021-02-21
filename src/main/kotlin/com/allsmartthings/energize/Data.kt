package com.allsmartthings.energize

import java.math.BigDecimal
import java.time.Year
import java.time.YearMonth

data class EegByYear(
        val date: Year,
        val value: BigDecimal,
        val lowerKwpBound: Int,
        val upperKwpBound: Int?
)

data class EegByYearMonth(
        val date: YearMonth,
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

data class EegWithSelfConsumption(
        val date: YearMonth,
        val value: BigDecimal,
        val lowerKwpBound: Int,
        val upperKwpBound: Int,
        val aboveThreshold: Boolean
)