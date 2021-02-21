package com.allsmartthings.energize

import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.math.BigDecimal
import java.time.Year
import java.time.YearMonth

object EegTsvConstantsTill2008 {
    const val YEAR_MONTH = 0
    const val BELOW_THIRTY_KWP = 1
    const val ABOVE_THIRTY_BELOW_HUNDRED_KWP = 2
    const val ABOVE_HUNDRED_BELOW_THOUSAND_KWP = 3
    const val ABOVE_THOUSAND_KWP = 4
    const val OPEN_SPACE_SYSTEM = 5
    const val FACADE_ENCLOSURE = 6
}

@Service
class EegTsvTill2008Reader() {

    private val internalEegByYearList = mutableListOf<EegByYear>()
    private val internalOpenSpaceSystemEegList = mutableListOf<OpenSpaceSystemEeg>()
    private val internalFacadeEnclosureList = mutableListOf<EegFacadeEnclosure>()

    val eegByYearList: List<EegByYear> = internalEegByYearList
    val openSpaceSystemEegList: List<OpenSpaceSystemEeg> = internalOpenSpaceSystemEegList
    val facadeEnclosureList: List<EegFacadeEnclosure> = internalFacadeEnclosureList

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
                val tokens = line.split("\t");
                val date = tokens[EegTsvConstantsTill2008.YEAR_MONTH]
                val belowThirtyKwp = tokens[EegTsvConstantsTill2008.BELOW_THIRTY_KWP]
                val aboveThirtyBelowHundred = tokens[EegTsvConstantsTill2008.ABOVE_THIRTY_BELOW_HUNDRED_KWP]
                val aboveHundredBelowThousand = tokens[EegTsvConstantsTill2008.ABOVE_HUNDRED_BELOW_THOUSAND_KWP]
                val aboveThousand = tokens[EegTsvConstantsTill2008.ABOVE_THOUSAND_KWP]
                val openSpaceSystem = tokens[EegTsvConstantsTill2008.OPEN_SPACE_SYSTEM]
                val facadeEnclosure = tokens[EegTsvConstantsTill2008.FACADE_ENCLOSURE]

                internalEegByYearList.addAll(
                        listOf(
                                EegByYear(
                                        Year.of(Integer.parseInt(date)),
                                        BigDecimal(belowThirtyKwp),
                                        0,
                                        30
                                ),
                                EegByYear(
                                        Year.of(Integer.parseInt(date)),
                                        BigDecimal(aboveThirtyBelowHundred),
                                        31,
                                        100
                                ),
                                EegByYear(
                                        Year.of(Integer.parseInt(date)),
                                        BigDecimal(aboveHundredBelowThousand),
                                        101,
                                        1000
                                ),
                                EegByYear(
                                        Year.of(Integer.parseInt(date)),
                                        BigDecimal(aboveThousand),
                                        1001,
                                        null
                                ),
                        )
                )

                internalFacadeEnclosureList.add(
                        EegFacadeEnclosure(
                                YearMonth.now(),
                                BigDecimal(facadeEnclosure)
                        )
                )

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


}