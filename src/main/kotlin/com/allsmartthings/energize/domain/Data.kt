package com.allsmartthings.energize.domain

import java.math.BigDecimal

data class Eeg(
        val date: String,
        val value: BigDecimal
)