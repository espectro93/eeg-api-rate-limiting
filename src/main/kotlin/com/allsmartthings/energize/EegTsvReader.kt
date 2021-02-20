package com.allsmartthings.energize

import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.math.BigDecimal
import java.time.YearMonth

object EegTsvConstants {
    const val YEAR_MONTH = 0
    const val BELOW_THIRTY_KWP = 1
    const val ABOVE_THIRTY_BELOW_HUNDRED_KWP = 2
    const val ABOVE_HUNDRED_BELOW_THOUSAND_KWP = 3
    const val ABOVE_THOUSAND_KWP = 4
    const val OPEN_SPACE_SYSTEM = 5
    const val FACADE_ENCLOSURE = 6
}

@Service
class EegTsvReader() {

    fun readEeg() {
        var fileReader: BufferedReader? = null

        val eegList = ArrayList<Eeg>()
        val openSpaceSystemEegList = ArrayList<OpenSpaceSystemEeg>()
        val facadeEnclosureList = ArrayList<EegFacadeEnclosure>()

        try {

            var line: String?

            fileReader = BufferedReader(FileReader("src/main/resources/data/eeg_bis_2008.tsv"))

            fileReader.readLine()
            line = fileReader.readLine()


            while (line != null) {
                val tokens = line.split("\t");
                val date = tokens[EegTsvConstants.YEAR_MONTH]
                val belowThirtyKwp = tokens[EegTsvConstants.BELOW_THIRTY_KWP]
                val aboveThirtyBelowHundred = tokens[EegTsvConstants.ABOVE_THIRTY_BELOW_HUNDRED_KWP]
                val aboveHundredBelowThousand = tokens[EegTsvConstants.ABOVE_HUNDRED_BELOW_THOUSAND_KWP]
                val aboveThousand = tokens[EegTsvConstants.ABOVE_THOUSAND_KWP]
                val openSpaceSystem = tokens[EegTsvConstants.OPEN_SPACE_SYSTEM]
                val facadeEnclosure = tokens[EegTsvConstants.FACADE_ENCLOSURE]

                eegList.addAll(
                        listOf(
                                Eeg(
                                        YearMonth.of(Integer.parseInt(date), 1),
                                        BigDecimal(belowThirtyKwp),
                                        0,
                                        30
                                ),
                                Eeg(
                                        YearMonth.of(Integer.parseInt(date), 1),
                                        BigDecimal(aboveThirtyBelowHundred),
                                        31,
                                        100
                                ),
                                Eeg(
                                        YearMonth.of(Integer.parseInt(date), 1),
                                        BigDecimal(aboveHundredBelowThousand),
                                        101,
                                        1000
                                ),
                                Eeg(
                                        YearMonth.of(Integer.parseInt(date), 1),
                                        BigDecimal(aboveThousand),
                                        1001,
                                        null
                                ),
                        )
                )

                facadeEnclosureList.add(
                        EegFacadeEnclosure(
                                YearMonth.now(),
                                BigDecimal(facadeEnclosure)
                        )
                )

                if (openSpaceSystem.contains("kW:")) {
                    var upperBound = openSpaceSystem.substringBefore("kW:")
                    upperBound = upperBound.replace("< ", "")
                    upperBound = upperBound.replace(" ", "")

                    openSpaceSystemEegList.add(
                            OpenSpaceSystemEeg(
                                    YearMonth.now(),
                                    BigDecimal(openSpaceSystem.substringAfter("kW: ")),
                                    Integer.parseInt(upperBound)
                            )
                    )
                    line = fileReader.readLine()
                    continue
                }

                openSpaceSystemEegList.add(
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