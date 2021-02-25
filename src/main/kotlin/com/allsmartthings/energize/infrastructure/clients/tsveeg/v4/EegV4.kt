package com.allsmartthings.energize.infrastructure.clients.tsveeg.v4

import com.allsmartthings.energize.YearResolution
import com.allsmartthings.energize.infrastructure.clients.tsveeg.Eeg
import com.allsmartthings.energize.infrastructure.clients.tsveeg.EegType
import java.time.Year

data class EegV4Internal(
    val internalEegYearResolutionMap: MutableMap<Year, YearResolution> = mutableMapOf(),
    val internalEegByYearMonthList: MutableList<EegType.EegByYearMonth> = mutableListOf(),
    val internalNotResidentialBuildingExteriorEegList: MutableList<EegType.NotResidentialBuildingExteriorEeg> = mutableListOf(),
    val internalSystemOnSealedOrConversionAreaList: MutableList<EegType.SystemOnSealedOrConversionAreaEeg> = mutableListOf()
) {}

data class EegV4(
    val eegByYearMonthList: MutableList<EegType.EegByYearMonth>,
    val notResidentialBuildingExteriorEegList: MutableList<EegType.NotResidentialBuildingExteriorEeg>,
    val systemOnSealedOrConversionAreaList: MutableList<EegType.SystemOnSealedOrConversionAreaEeg>
) : Eeg {
    companion object {
        fun fromInternalData(data: EegV4Internal): EegV4 {
            return EegV4(
                data.internalEegByYearMonthList,
                data.internalNotResidentialBuildingExteriorEegList,
                data.internalSystemOnSealedOrConversionAreaList
            )
        }
    }
}