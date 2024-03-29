package com.allsmartthings.energize.infrastructure.clients.tsveeg.v2

import com.allsmartthings.energize.infrastructure.clients.tsveeg.EegMarker
import com.allsmartthings.energize.infrastructure.clients.tsveeg.EegType
import com.allsmartthings.energize.infrastructure.clients.tsveeg.YearResolution
import java.time.Year

data class EegV2Internal(
        val internalYearResolutionMap: MutableMap<Year, YearResolution> = mutableMapOf(),
        val internalEegByYearMonthList: MutableList<EegType.EegByYearMonth> = mutableListOf(),
        val internalEegWithSelfConsumptionByYearMonthList: MutableList<EegType.EegWithSelfConsumptionByYearMonth> = mutableListOf(),
        val internalSystemOnSealedOrConversionAreaList: MutableList<EegType.SystemOnSealedOrConversionAreaEeg> = mutableListOf(),
        val internalSystemOnAcreList: MutableList<EegType.SystemOnAcreEeg> = mutableListOf(),
        val internalOpenSpaceSystemEegList: MutableList<EegType.OpenSpaceSystemEeg> = mutableListOf()
)

data class EegV2(
        override val yearResolutionMap: Map<Year, YearResolution>,
        override val eegByYearMonthList: List<EegType.EegByYearMonth>,
        val eegWithSelfConsumptionByYearMonthList: List<EegType.EegWithSelfConsumptionByYearMonth>,
        val eegSystemOnSealedOrConversionAreaEegList: List<EegType.SystemOnSealedOrConversionAreaEeg>,
        val eegSystemOnAcreList: List<EegType.SystemOnAcreEeg>,
        val openSpaceSystemEegList: List<EegType.OpenSpaceSystemEeg>
) : EegMarker {
    companion object {
        fun fromInternalData(data: EegV2Internal): EegV2 {
            return EegV2(
                    data.internalYearResolutionMap,
                    data.internalEegByYearMonthList,
                    data.internalEegWithSelfConsumptionByYearMonthList,
                    data.internalSystemOnSealedOrConversionAreaList,
                    data.internalSystemOnAcreList,
                    data.internalOpenSpaceSystemEegList
            )
        }
    }
}