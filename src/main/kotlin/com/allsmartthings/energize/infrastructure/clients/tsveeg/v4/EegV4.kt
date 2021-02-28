package com.allsmartthings.energize.infrastructure.clients.tsveeg.v4

import com.allsmartthings.energize.infrastructure.clients.tsveeg.EegMarker
import com.allsmartthings.energize.infrastructure.clients.tsveeg.EegType
import com.allsmartthings.energize.infrastructure.clients.tsveeg.YearResolution
import java.time.Year

data class EegV4Internal(
        val internalEegYearResolutionMap: MutableMap<Year, YearResolution> = mutableMapOf(),
        val internalEegByYearMonthList: MutableList<EegType.EegByYearMonth> = mutableListOf(),
        val internalNotResidentialBuildingExteriorEegList: MutableList<EegType.NotResidentialBuildingExteriorEeg> = mutableListOf(),
        val internalSystemOnSealedOrConversionAreaList: MutableList<EegType.SystemOnSealedOrConversionAreaEeg> = mutableListOf()
) {}

data class EegV4(
        override val yearResolutionMap: Map<Year, YearResolution>,
        override val eegByYearMonthList: MutableList<EegType.EegByYearMonth>,
        val notResidentialBuildingExteriorEegList: MutableList<EegType.NotResidentialBuildingExteriorEeg>,
        val systemOnSealedOrConversionAreaList: MutableList<EegType.SystemOnSealedOrConversionAreaEeg>
) : EegMarker {
    companion object {
        fun fromInternalData(data: EegV4Internal): EegV4 {
            return EegV4(
                    data.internalEegYearResolutionMap,
                    data.internalEegByYearMonthList,
                    data.internalNotResidentialBuildingExteriorEegList,
                    data.internalSystemOnSealedOrConversionAreaList
            )
        }
    }
}