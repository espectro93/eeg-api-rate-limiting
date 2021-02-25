package com.allsmartthings.energize.infrastructure.clients.tsveeg.v5

import com.allsmartthings.energize.YearResolution
import com.allsmartthings.energize.infrastructure.clients.tsveeg.*
import com.allsmartthings.energize.infrastructure.clients.tsveeg.v5.EegV5
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.math.BigDecimal
import java.time.Year
import java.time.YearMonth

@Service
class EegV5Reader : EegTsvParser<EegV5> {
    private val internalEegData: EegV5Internal = EegV5Internal()
    
    override fun getYearResolutionMap(): MutableMap<Year, YearResolution> {
        return internalEegData.internalEegYearResolutionMap
    }

    override fun readFromTsv(): EegV5 {
        var fileReader: BufferedReader? = null

        try {
            var line: String?
            fileReader = BufferedReader(FileReader("src/main/resources/data/eeg_V5.tsv"))
            fileReader.readLine()
            line = fileReader.readLine()

            while (line != null) {
                val eegVars = EegV5Vars.parseFromRawInput(line);
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
        return EegV5.fromInternalData(internalEegData)
    }

    private fun populateEegByYearMonthList(
        eegDate: YearMonth,
        eegVars: EegV5Vars
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
                    100
                )
            )
        )
    }
}