package com.allsmartthings.energize.infrastructure.clients.tsveeg

import com.allsmartthings.energize.domain.Eeg
import java.math.BigDecimal
import java.time.Year
import java.time.YearMonth

sealed class EegType {
    data class EegByYearMonth(val date: YearMonth, val rangeValues: List<EegForKwpRange>) : EegType()
    data class OpenSpaceSystemEeg(val date: YearMonth, val value: BigDecimal, val upperKwpBound: Int?) : EegType()
    data class EegFacadeEnclosure(val date: YearMonth, val value: BigDecimal) : EegType()
    data class SystemOnSealedOrConversionAreaEeg(val date: YearMonth, val value: BigDecimal) : EegType()
    data class SystemOnAcreEeg(val date: YearMonth, val value: BigDecimal)
    data class NotResidentialBuildingExteriorEeg(val date: YearMonth, val value: BigDecimal) : EegType()
    data class EegWithSelfConsumptionByYearMonth(
            val date: YearMonth,
            val rangeValues: List<EegForKwpRange>,
            val aboveThreshold: Boolean
    ) : EegType()
}

data class EegForKwpRange(
        val value: BigDecimal,
        val lowerKwpBound: Int,
        val upperKwpBound: Int?
)

interface EegMarker {
    val yearResolutionMap: Map<Year, YearResolution>
    val eegByYearMonthList: List<EegType.EegByYearMonth>
}


fun EegMarker.getEegListFor(kwp: BigDecimal, date: YearMonth): Eeg {
    val dateFromResolution = resolveDateWithResolutionMap(date)

    return eegByYearMonthList.stream()
            .filter { eegByYearMonth -> eegByYearMonth.date == dateFromResolution }
            .map { eegByYearMonth ->
                eegByYearMonth.rangeValues
                        .filter { eegForKwpRange ->
                            eegForKwpRange.lowerKwpBound <= kwp.toInt() && (
                                    eegForKwpRange.upperKwpBound?.let { it >= kwp.toInt() } ?: true)
                        }.map { eegForKwpRange -> Eeg(date.toString(), eegForKwpRange.value) }
            }.findFirst()
            .orElseThrow()
            .stream()
            .findFirst()
            .orElseThrow()
}

private fun EegMarker.resolveDateWithResolutionMap(date: YearMonth): YearMonth {
    val resolution = yearResolutionMap[Year.of(date.year)]
    return if (resolution == YearResolution.YEARLY) YearMonth.of(date.year, 1) else date;
}