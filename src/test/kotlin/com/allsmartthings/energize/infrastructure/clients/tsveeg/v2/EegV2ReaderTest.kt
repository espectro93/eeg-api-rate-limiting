package com.allsmartthings.energize.infrastructure.clients.tsveeg.v2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class EegV2ReaderTest{
    private val eegTsvReader: EegV2Reader = EegV2Reader()

    @Test
    fun readEegTillMarch2009(){
        val eeg: EegV2 = eegTsvReader.readFromTsv()
        assertEquals(eeg.eegByYearMonthList.size, 17)
        assertEquals(eeg.openSpaceSystemEegList.size, 17)
    }
}