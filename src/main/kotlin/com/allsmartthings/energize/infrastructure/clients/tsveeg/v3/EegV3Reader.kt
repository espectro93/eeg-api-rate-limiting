package com.allsmartthings.energize.infrastructure.clients.tsveeg.v3

import com.allsmartthings.energize.infrastructure.clients.tsveeg.*
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.math.BigDecimal
import java.time.Year
import java.time.YearMonth

@Service
class EegV3Reader : EegTsvParser<EegV3> {
    private val internalEegData: EegV3Internal = EegV3Internal()

    override fun getYearResolutionMap(): MutableMap<Year, YearResolution> {
        return internalEegData.internalYearResolutionMap
    }

    override fun readFromTsv(): EegV3 {
        var fileReader: BufferedReader? = null

        try {
            var line: String?
            fileReader = BufferedReader(FileReader("src/main/resources/data/eeg_v3.tsv"))
            fileReader.readLine()
            line = fileReader.readLine()

            while (line != null) {
                val eegVars = EegV3Vars.parseFromRawInput(line);
                val eegDate = parseInputDate(eegVars.date)

                internalEegData.internalEegByYearMonthList.add(
                        populateEegByYearMonthList(
                                eegDate,
                                eegVars
                        )
                )

                internalEegData.internalNotResidentialBuildingExteriorEegList.add(
                        EegType.NotResidentialBuildingExteriorEeg(
                                eegDate,
                                BigDecimal(eegVars.notResidentialBuildingExterior)
                        )
                )

                internalEegData.internalSystemOnSealedOrConversionAreaList.add(
                        EegType.SystemOnSealedOrConversionAreaEeg(
                                eegDate,
                                BigDecimal(eegVars.systemOnSealedOrConversionArea)
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
        return EegV3.fromInternalData(internalEegData)
    }

    private fun populateEegByYearMonthList(
            eegDate: YearMonth,
            eegVars: EegV3Vars
    ): EegType.EegByYearMonth {
        return EegType.EegByYearMonth(
                eegDate,
                listOf(
                        EegForKwpRange(
                                BigDecimal(eegVars.belowTenKwp),
                                0,
                                10
                        ),
                        EegForKwpRange(
                                BigDecimal(eegVars.aboveTenBelowFortyKwp),
                                11,
                                40
                        ),
                        EegForKwpRange(
                                BigDecimal(eegVars.aboveFortyBelowThousandKwp),
                                41,
                                1000
                        ),
                        EegForKwpRange(
                                BigDecimal(eegVars.aboveThousandBelowTenThousandKwp),
                                1001,
                                10000
                        )
                )
        )

    }
}