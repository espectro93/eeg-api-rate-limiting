package com.allsmartthings.energize.infrastructure.clients.tsveeg.v2

import com.allsmartthings.energize.infrastructure.clients.tsveeg.*
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.math.BigDecimal
import java.time.Year
import java.time.YearMonth

@Service
class EegV2Reader() : EegTsvParser<EegV2> {
    private val internalEegData: EegV2Internal = EegV2Internal()

    override fun readFromTsv(): EegV2 {
        var fileReader: BufferedReader? = null

        try {
            var line: String?
            fileReader = BufferedReader(FileReader("src/main/resources/data/eeg_v2.tsv"))
            fileReader.readLine()
            line = fileReader.readLine()

            while (line != null) {
                val eegVars = EegV2Vars.parseFromRawInput(line);
                val eegDate = parseInputDate(eegVars.date)

                internalEegData.internalEegByYearMonthList.add(
                        populateEegByYearMonthList(
                                eegDate,
                                eegVars
                        )
                )

                internalEegData.internalEegWithSelfConsumptionByYearMonthList.addAll(
                        populateEegByYearMonthWithSelfConsumptionList(
                                eegDate,
                                eegVars
                        )
                )

                internalEegData.internalOpenSpaceSystemEegList.add(
                        EegType.OpenSpaceSystemEeg(
                                eegDate,
                                BigDecimal(eegVars.openSpaceSystem),
                                null
                        )
                )

                internalEegData.internalSystemOnSealedOrConversionAreaList.add(
                        EegType.SystemOnSealedOrConversionAreaEeg(
                                eegDate,
                                BigDecimal(eegVars.systemOnSealedOrConversionArea)
                        )
                )

                internalEegData.internalSystemOnAcreList.add(
                        EegType.SystemOnAcreEeg(
                                eegDate,
                                BigDecimal(eegVars.systemOnAcre)
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
        return EegV2.fromInternalData(internalEegData)
    }


    private fun populateEegByYearMonthWithSelfConsumptionList(
            eegDate: YearMonth,
            eegVars: EegV2Vars
    ): List<EegType.EegWithSelfConsumptionByYearMonth> {
        return listOf(
                EegType.EegWithSelfConsumptionByYearMonth(
                        eegDate,
                        listOf(
                                EegForKwpRange(
                                        BigDecimal(eegVars.belowThirtyKwpSelfConsumptionBelowThreshold),
                                        0,
                                        30
                                ),
                                EegForKwpRange(
                                        BigDecimal(eegVars.aboveThirtyBelowHundredKwpSelfConsumptionBelowThreshold),
                                        31,
                                        100
                                ),
                                EegForKwpRange(
                                        BigDecimal(eegVars.aboveHundredBelowFiveHundredKwpSelfConsumptionBelowThreshold),
                                        101,
                                        500
                                )
                        ),
                        false
                ),
                EegType.EegWithSelfConsumptionByYearMonth(
                        eegDate,
                        listOf(
                                EegForKwpRange(
                                        BigDecimal(eegVars.belowThirtyKwpSelfConsumptionAboveThreshold),
                                        0,
                                        30
                                ),
                                EegForKwpRange(
                                        BigDecimal(eegVars.aboveThirtyBelowHundredKwpSelfConsumptionAboveThreshold),
                                        31,
                                        100
                                ),
                                EegForKwpRange(
                                        BigDecimal(eegVars.aboveHundredBelowFiveHundredKwpSelfConsumptionAboveThreshold),
                                        101,
                                        500
                                )
                        ),
                        true
                )
        )
    }

    private fun populateEegByYearMonthList(
            eegDate: YearMonth,
            eegVars: EegV2Vars
    ): EegType.EegByYearMonth {
        return EegType.EegByYearMonth(
                eegDate,
                listOf(
                        EegForKwpRange(
                                BigDecimal(eegVars.belowThirtyKwp),
                                0,
                                30
                        ),
                        EegForKwpRange(
                                BigDecimal(eegVars.aboveThirtyBelowHundred),
                                31,
                                100
                        ),
                        EegForKwpRange(
                                BigDecimal(eegVars.aboveHundredBelowThousand),
                                101,
                                1000
                        ),
                        EegForKwpRange(
                                BigDecimal(eegVars.aboveThousand),
                                1001,
                                null
                        )
                )
        )

    }

    override fun getYearResolutionMap(): MutableMap<Year, YearResolution> {
        return internalEegData.internalYearResolutionMap
    }
}
