package com.allsmartthings.energize.infrastructure.clients.tsveeg.v1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class EegV1ReaderTest{

    private val eegTsvReader: EegV1Reader = EegV1Reader()

    @Test
    fun readEegTill2008(){
        val eeg: EegV1 = eegTsvReader.readFromTsv()
        assertEquals(eeg.eegByYearMonthList.size, 36)
        assertEquals(eeg.facadeEnclosureList.size, 9)
        assertEquals(eeg.openSpaceSystemEegList.size, 9)
    }
}