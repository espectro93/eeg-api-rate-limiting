package com.allsmartthings.energize.infrastructure.clients.tsveeg.v4

import com.allsmartthings.energize.infrastructure.clients.tsveeg.YearResolution
import com.allsmartthings.energize.infrastructure.clients.tsveeg.*
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.math.BigDecimal
import java.time.Year
import java.time.YearMonth

@Service
class EegV4Reader : EegTsvParser<EegV4> {
    private val internalEegData: EegV4Internal = EegV4Internal()

    override fun getYearResolutionMap(): MutableMap<Year, YearResolution> {
        return internalEegData.internalEegYearResolutionMap
    }

    override fun readFromTsv(): EegV4 {
        var fileReader: BufferedReader? = null

        try {
            var line: String?
            fileReader = BufferedReader(FileReader("src/main/resources/data/eeg_v4.tsv"))
            fileReader.readLine()
            line = fileReader.readLine()

            while (line != null) {
                val eegVars = EegV4Vars.parseFromRawInput(line);
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
        return EegV4.fromInternalData(internalEegData)
    }

    private fun populateEegByYearMonthList(
        eegDate: YearMonth,
        eegVars: EegV4Vars
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
                    BigDecimal(eegVars.aboveFortyBelowFiveHundredKwp),
                    41,
                    1000
                )
            )
        )
    }
}