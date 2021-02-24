package com.allsmartthings.energize.infrastructure.clients.tsveeg.v1

import com.allsmartthings.energize.YearResolution
import com.allsmartthings.energize.infrastructure.clients.tsveeg.*
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.math.BigDecimal
import java.time.Year
import java.time.YearMonth

@Service
class EegV1Reader() : EegTsvParser<EegV1> {
    private val internalEegData: EegV1Internal = EegV1Internal()

    init {
        readFromTsv()
    }

    private fun readFromTsv() {
        var fileReader: BufferedReader? = null

        try {
            var line: String?
            fileReader = BufferedReader(FileReader("src/main/resources/data/eeg_bis_2008.tsv"))
            fileReader.readLine()
            line = fileReader.readLine()

            while (line != null) {
                val eegVars = EegV1Vars.parseFromRawInput(line)
                val eegDate: YearMonth = parseInputDate(eegVars.date)

                internalEegData.internalEegByYearList.addAll(populateEegByYearMonthList(eegDate, eegVars))

                internalEegData.internalFacadeEnclosureList.add(
                    EegType.EegFacadeEnclosure(
                        eegDate,
                        BigDecimal(eegVars.facadeEnclosure)
                    )
                )

                if (eegVars.openSpaceSystem.contains("kW:")) {
                    var upperBound = eegVars.openSpaceSystem.substringBefore("kW:")
                    upperBound = upperBound.replace("< ", "")
                    upperBound = upperBound.replace(" ", "")

                    internalEegData.internalOpenSpaceSystemEegList.add(
                        EegType.OpenSpaceSystemEeg(
                            eegDate,
                            BigDecimal(eegVars.openSpaceSystem.substringAfter("kW: ")),
                            Integer.parseInt(upperBound)
                        )
                    )
                    line = fileReader.readLine()
                    continue
                }

                internalEegData.internalOpenSpaceSystemEegList.add(
                    EegType.OpenSpaceSystemEeg(
                        eegDate,
                        BigDecimal(eegVars.openSpaceSystem),
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

    private fun populateEegByYearMonthList(eegDate: YearMonth, eegVars: EegV1Vars): List<EegType.EegByYearMonth> {
        return listOf(
            EegType.EegByYearMonth(
                eegDate,
                listOf(
                    EegForKwpRange(
                        BigDecimal(eegVars.belowThirtyKwp),
                        0,
                        30
                    )
                )
            ),
            EegType.EegByYearMonth(
                eegDate,
                listOf(
                    EegForKwpRange(
                        BigDecimal(eegVars.aboveThirtyBelowHundred),
                        31,
                        100
                    )
                )
            ),
            EegType.EegByYearMonth(
                eegDate,
                listOf(
                    EegForKwpRange(
                        BigDecimal(eegVars.aboveHundredBelowThousand),
                        101,
                        1000
                    )
                )
            ),
            EegType.EegByYearMonth(
                eegDate,
                listOf(
                    EegForKwpRange(
                        BigDecimal(eegVars.aboveThousand),
                        1001,
                        null
                    )
                )
            ),
        )
    }

    override fun getYearResolutionMap(): MutableMap<Year, YearResolution> {
        return internalEegData.internalYearResolutionMap
    }

    override fun getEeg(): EegV1 {
        return EegV1.fromInternalData(internalEegData)
    }
}