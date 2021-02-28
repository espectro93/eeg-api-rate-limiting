package com.allsmartthings.energize.infrastructure.clients.tsveeg.v1

import com.allsmartthings.energize.YearResolution
import com.allsmartthings.energize.infrastructure.clients.tsveeg.EegMarker
import com.allsmartthings.energize.infrastructure.clients.tsveeg.EegType
import java.time.Year

data class EegV1Internal(
    val internalYearResolutionMap: MutableMap<Year, YearResolution> = mutableMapOf(),
    val internalEegByYearList: MutableList<EegType.EegByYearMonth> = mutableListOf(),
    val internalOpenSpaceSystemEegList: MutableList<EegType.OpenSpaceSystemEeg> = mutableListOf(),
    val internalFacadeEnclosureList: MutableList<EegType.EegFacadeEnclosure> = mutableListOf()
)

data class EegV1(
    val yearResolutionMap: Map<Year, YearResolution>,
    val eegByYearList: List<EegType.EegByYearMonth>,
    val openSpaceSystemEegList: List<EegType.OpenSpaceSystemEeg>,
    val facadeEnclosureList: List<EegType.EegFacadeEnclosure>
) : EegMarker {
    companion object {
        fun fromInternalData(data: EegV1Internal): EegV1 {
            return EegV1(
                data.internalYearResolutionMap,
                data.internalEegByYearList,
                data.internalOpenSpaceSystemEegList,
                data.internalFacadeEnclosureList
            )
        }
    }
}