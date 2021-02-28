package com.allsmartthings.energize.infrastructure.clients.tsveeg.v3

import com.allsmartthings.energize.infrastructure.clients.tsveeg.EegMarker
import com.allsmartthings.energize.infrastructure.clients.tsveeg.EegType
import com.allsmartthings.energize.infrastructure.clients.tsveeg.YearResolution
import java.time.Year

data class EegV3Internal(
        val internalYearResolutionMap: MutableMap<Year, YearResolution> = mutableMapOf(),
        val internalEegByYearMonthList: MutableList<EegType.EegByYearMonth> = mutableListOf(),
        val internalNotResidentialBuildingExteriorEegList: MutableList<EegType.NotResidentialBuildingExteriorEeg> = mutableListOf(),
        val internalSystemOnSealedOrConversionAreaList: MutableList<EegType.SystemOnSealedOrConversionAreaEeg> = mutableListOf()
)

data class EegV3(
        override val yearResolutionMap: Map<Year, YearResolution>,
        override val eegByYearMonthList: MutableList<EegType.EegByYearMonth>,
        val notResidentialBuildingExteriorEegList: MutableList<EegType.NotResidentialBuildingExteriorEeg>,
        val systemOnSealedOrConversionAreaList: MutableList<EegType.SystemOnSealedOrConversionAreaEeg>
) : EegMarker {
    companion object {
        fun fromInternalData(data: EegV3Internal): EegV3 {
            return EegV3(
                    data.internalYearResolutionMap,
                    data.internalEegByYearMonthList,
                    data.internalNotResidentialBuildingExteriorEegList,
                    data.internalSystemOnSealedOrConversionAreaList
            )
        }
    }
}