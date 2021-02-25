package com.allsmartthings.energize.infrastructure.clients.tsveeg.v2

import com.allsmartthings.energize.YearResolution
import com.allsmartthings.energize.infrastructure.clients.tsveeg.Eeg
import com.allsmartthings.energize.infrastructure.clients.tsveeg.EegType
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
    val yearResolutionMap: Map<Year, YearResolution>,
    val eegByYearMonthList: List<EegType.EegByYearMonth>,
    val eegWithSelfConsumptionByYearMonthList: List<EegType.EegWithSelfConsumptionByYearMonth>,
    val eegSystemOnSealedOrConversionAreaEegList: List<EegType.SystemOnSealedOrConversionAreaEeg>,
    val eegSystemOnAcreList: List<EegType.SystemOnAcreEeg>,
    val openSpaceSystemEegList: List<EegType.OpenSpaceSystemEeg>
): Eeg {
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