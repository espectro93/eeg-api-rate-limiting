package com.allsmartthings.energize

import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeParseException

object EegTsvConstants2009TillMarch2012 {
    const val YEAR_MONTH = 0
    const val BELOW_THIRTY_KWP = 1
    const val ABOVE_THIRTY_BELOW_HUNDRED_KWP = 2
    const val ABOVE_HUNDRED_BELOW_THOUSAND_KWP = 3
    const val ABOVE_THOUSAND_KWP = 4
    const val BELOW_THIRTY_KWP_SELF_CONSUMPTION_TILL_THIRTY_PERCENT = 5
    const val BELOW_THIRTY_KWP_SELF_CONSUMPTION_ABOVE_THIRTY_PERCENT = 6
    const val ABOVE_THIRTY_BELOW_HUNDRED_KWP_SELF_CONSUMPTION_TILL_THIRTY_PERCENT = 7
    const val ABOVE_THIRTY_BELOW_HUNDRED_KWP_SELF_CONSUMPTION_ABOVE_THIRTY_PERCENT = 8
    const val ABOVE_HUNDRED_BELOW_FIVE_HUNDRED_KWP_SELF_CONSUMPTION_TILL_THIRTY_PERCENT = 9
    const val ABOVE_HUNDRED_BELOW_FIVE_HUNDRED_KWP_SELF_CONSUMPTION_ABOVE_THIRTY_PERCENT = 10
    const val SYSTEM_ON_SEALED_OR_CONVERSION_AREA = 11
    const val OPEN_SPACE_SYSTEM = 12
    const val SYSTEM_ON_ACRE = 13
}

enum class YearResolution {
    YEARLY,
    MONTHLY
}

@Service
class EegTsv2009TillMarch2012Reader() {

    private val yearResolutionMap = mutableMapOf<Year, YearResolution>()

    private val internalEegByYearList = mutableListOf<EegByYear>()
    private val internalEegByYearMonthList = mutableListOf<EegByYearMonth>()
    private val internalEegWithSelfConsumptionByYearList = mutableListOf<EegWithSelfConsumptionByYear>()
    private val internalEegWithSelfConsumptionByYearMonthList = mutableListOf<EegWithSelfConsumptionByYearMonth>()

    private val internalOpenSpaceSystemEegList = mutableListOf<OpenSpaceSystemEeg>()
    private val internalFacadeEnclosureList = mutableListOf<EegFacadeEnclosure>()

    val eegByYearList: List<EegByYear> = internalEegByYearList
    val eegByYearMonthList: List<EegByYearMonth> = internalEegByYearMonthList
    val openSpaceSystemEegList: List<OpenSpaceSystemEeg> = internalOpenSpaceSystemEegList
    val facadeEnclosureList: List<EegFacadeEnclosure> = internalFacadeEnclosureList

    //TODO:ALLE EEG TYPES AU?ERHALB DER RANGES MIT ENUM WERTEN GENERALISIEREN

    init {
        readFromTsv()
    }

    private fun readFromTsv() {
        var fileReader: BufferedReader? = null

        try {

            var line: String?

            fileReader = BufferedReader(FileReader("src/main/resources/data/eeg_2009_maerz_2012.tsv"))

            fileReader.readLine()
            line = fileReader.readLine()


            while (line != null) {
                val tokens = line.split("\t");
                val date = tokens[EegTsvConstants2009TillMarch2012.YEAR_MONTH]
                val belowThirtyKwp = tokens[EegTsvConstants2009TillMarch2012.BELOW_THIRTY_KWP]
                val aboveThirtyBelowHundred = tokens[EegTsvConstants2009TillMarch2012.ABOVE_THIRTY_BELOW_HUNDRED_KWP]
                val aboveHundredBelowThousand =
                    tokens[EegTsvConstants2009TillMarch2012.ABOVE_HUNDRED_BELOW_THOUSAND_KWP]
                val aboveThousand = tokens[EegTsvConstants2009TillMarch2012.ABOVE_THOUSAND_KWP]
                val openSpaceSystem = tokens[EegTsvConstants2009TillMarch2012.OPEN_SPACE_SYSTEM]

                var parsedYear: Year
                if (isYear(date)) {
                    parsedYear = Year.parse(date)
                    yearResolutionMap.putIfAbsent(parsedYear, YearResolution.YEARLY)
                } else {
                    parsedYear = Year.of(LocalDate.parse(date).year)
                    yearResolutionMap.putIfAbsent(parsedYear, YearResolution.MONTHLY)
                }

                val belowThirtyKwpSelfConsumptionBelowThreshold = tokens[EegTsvConstants2009TillMarch2012
                    .BELOW_THIRTY_KWP_SELF_CONSUMPTION_TILL_THIRTY_PERCENT]
                val belowThirtyKwpSelfConsumptionAboveThreshold = tokens[EegTsvConstants2009TillMarch2012
                    .BELOW_THIRTY_KWP_SELF_CONSUMPTION_ABOVE_THIRTY_PERCENT]

                val aboveThirtyBelowHundredKwpSelfConsumptionBelowThreshold =
                    tokens[EegTsvConstants2009TillMarch2012.ABOVE_THIRTY_BELOW_HUNDRED_KWP_SELF_CONSUMPTION_TILL_THIRTY_PERCENT]
                val aboveThirtyBelowHundredKwpSelfConsumptionAboveThreshold = tokens[EegTsvConstants2009TillMarch2012
                    .ABOVE_THIRTY_BELOW_HUNDRED_KWP_SELF_CONSUMPTION_ABOVE_THIRTY_PERCENT]

                val aboveHundredBelowFiveHundredKwpSelfConsumptionBelowThreshold =
                    tokens[EegTsvConstants2009TillMarch2012
                        .ABOVE_HUNDRED_BELOW_FIVE_HUNDRED_KWP_SELF_CONSUMPTION_TILL_THIRTY_PERCENT]
                val aboveHundredBelowFiveHundredKwpSelfConsumptionAboveThreshold =
                    tokens[EegTsvConstants2009TillMarch2012
                        .ABOVE_HUNDRED_BELOW_FIVE_HUNDRED_KWP_SELF_CONSUMPTION_ABOVE_THIRTY_PERCENT]


                val systemOnSealedOrConversionArea =
                    tokens[EegTsvConstants2009TillMarch2012.SYSTEM_ON_SEALED_OR_CONVERSION_AREA]
                val systemOnAcre = tokens[EegTsvConstants2009TillMarch2012.SYSTEM_ON_ACRE]

                yearResolutionMap[parsedYear]?.let {
                    if (it == YearResolution.MONTHLY) {
                        var eegDate: YearMonth = YearMonth.from(LocalDate.parse(date))
                        internalEegByYearMonthList.add(
                            EegByYearMonth(
                                eegDate,
                                listOf(
                                    EegForKwpRange(
                                        BigDecimal(belowThirtyKwp),
                                        0,
                                        30
                                    ),
                                    EegForKwpRange(
                                        BigDecimal(aboveThirtyBelowHundred),
                                        31,
                                        100
                                    ),
                                    EegForKwpRange(
                                        BigDecimal(aboveHundredBelowThousand),
                                        101,
                                        1000
                                    ),
                                    EegForKwpRange(
                                        BigDecimal(aboveThousand),
                                        1001,
                                        null
                                    )
                                )
                            )
                        )

                        internalEegWithSelfConsumptionByYearMonthList.addAll(
                            listOf(
                                EegWithSelfConsumptionByYearMonth(
                                    eegDate,
                                    listOf(
                                        EegForKwpRange(
                                            BigDecimal(belowThirtyKwpSelfConsumptionBelowThreshold),
                                            0,
                                            30
                                        ),
                                        EegForKwpRange(
                                            BigDecimal(aboveThirtyBelowHundredKwpSelfConsumptionBelowThreshold),
                                            31,
                                            100
                                        ),
                                        EegForKwpRange(
                                            BigDecimal(aboveHundredBelowFiveHundredKwpSelfConsumptionBelowThreshold),
                                            101,
                                            500
                                        )
                                    ),
                                    false
                                ),
                                EegWithSelfConsumptionByYearMonth(
                                    eegDate,
                                    listOf(
                                        EegForKwpRange(
                                            BigDecimal(belowThirtyKwpSelfConsumptionAboveThreshold),
                                            0,
                                            30
                                        ),
                                        EegForKwpRange(
                                            BigDecimal(aboveThirtyBelowHundredKwpSelfConsumptionAboveThreshold),
                                            31,
                                            100
                                        ),
                                        EegForKwpRange(
                                            BigDecimal(aboveHundredBelowFiveHundredKwpSelfConsumptionAboveThreshold),
                                            101,
                                            500
                                        )
                                    ),
                                    true
                                )
                            )
                        )
                    }
                }


                if (openSpaceSystem.contains("kW:")) {
                    var upperBound = openSpaceSystem.substringBefore("kW:")
                    upperBound = upperBound.replace("< ", "")
                    upperBound = upperBound.replace(" ", "")

                    internalOpenSpaceSystemEegList.add(
                        OpenSpaceSystemEeg(
                            YearMonth.now(),
                            BigDecimal(openSpaceSystem.substringAfter("kW: ")),
                            Integer.parseInt(upperBound)
                        )
                    )
                    line = fileReader.readLine()
                    continue
                }

                internalOpenSpaceSystemEegList.add(
                    OpenSpaceSystemEeg(
                        YearMonth.now(),
                        BigDecimal(openSpaceSystem),
                        null
                    )
                )
                line = fileReader.readLine()
            }
        } catch (e: Exception) {
            println("Error while reading tsv file")
            e.printStackTrace()
        } finally {
            try {
                fileReader!!.close()
            } catch (e: IOException) {
                println("Error during FileReader close")
                e.printStackTrace()
            }
        }
    }

    private fun isYear(year: String): Boolean {
        return try {
            Year.parse(year);
            true
        } catch (e: DateTimeParseException) {
            false
        }
    }
}