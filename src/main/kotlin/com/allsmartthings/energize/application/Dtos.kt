package com.allsmartthings.energize.application

import com.allsmartthings.energize.domain.Eeg

data class EegDto(
        val date: String = "",
        val value: Double = 0.0
){
    companion object{
        fun fromDomain(data: Eeg): EegDto{
            return EegDto(data.date, data.value.toDouble())
        }
    }
}