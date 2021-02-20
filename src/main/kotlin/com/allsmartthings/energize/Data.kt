package com.allsmartthings.energize

import java.math.BigDecimal
import java.time.YearMonth

data class Eeg(
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