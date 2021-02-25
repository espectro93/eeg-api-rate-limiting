package com.allsmartthings.energize.infrastructure.clients.tsveeg.v5

import com.allsmartthings.energize.YearResolution
import com.allsmartthings.energize.infrastructure.clients.tsveeg.Eeg
import com.allsmartthings.energize.infrastructure.clients.tsveeg.EegType
import java.time.Year


data class EegV5Internal(
    val internalEegYearResolutionMap: MutableMap<Year, YearResolution> = mutableMapOf(),
    val internalEegByYearMonthList: MutableList<EegType.EegByYearMonth> = mutableListOf(),
    val internalNotResidentialBuildingExteriorEegList: MutableList<EegType.NotResidentialBuildingExteriorEeg> = mutableListOf()
)

data class EegV5(
    val eegByYearMonthList: MutableList<EegType.EegByYearMonth>,
    val notResidentialBuildingExteriorEegList: MutableList<EegType.NotResidentialBuildingExteriorEeg>
) : Eeg {
    companion object {
        fun fromInternalData(data: EegV5Internal): EegV5 {
            return EegV5(
                data.internalEegByYearMonthList,
                data.internalNotResidentialBuildingExteriorEegList
            )
        }
    }
}