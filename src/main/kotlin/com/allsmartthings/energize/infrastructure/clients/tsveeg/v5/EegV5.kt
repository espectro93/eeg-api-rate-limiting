package com.allsmartthings.energize.infrastructure.clients.tsveeg.v5

import com.allsmartthings.energize.infrastructure.clients.tsveeg.EegMarker
import com.allsmartthings.energize.infrastructure.clients.tsveeg.EegType
import com.allsmartthings.energize.infrastructure.clients.tsveeg.YearResolution
import java.time.Year


data class EegV5Internal(
        val internalEegYearResolutionMap: MutableMap<Year, YearResolution> = mutableMapOf(),
        val internalEegByYearMonthList: MutableList<EegType.EegByYearMonth> = mutableListOf(),
        val internalNotResidentialBuildingExteriorEegList: MutableList<EegType.NotResidentialBuildingExteriorEeg> = mutableListOf()
)

data class EegV5(
        override val yearResolutionMap: Map<Year, YearResolution>,
        override val eegByYearMonthList: MutableList<EegType.EegByYearMonth>,
        val notResidentialBuildingExteriorEegList: MutableList<EegType.NotResidentialBuildingExteriorEeg>
) : EegMarker {
    companion object {
        fun fromInternalData(data: EegV5Internal): EegV5 {
            return EegV5(
                    data.internalEegYearResolutionMap,
                    data.internalEegByYearMonthList,
                    data.internalNotResidentialBuildingExteriorEegList
            )
        }
    }
}