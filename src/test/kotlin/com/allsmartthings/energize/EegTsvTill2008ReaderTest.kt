package com.allsmartthings.energize

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class EegTsvTill2008ReaderTest{

    private val eegTsvReader: EegTsvTill2008Reader = EegTsvTill2008Reader()

    @Test
    fun readEegTill2008(){
        assertEquals(eegTsvReader.eegByYearList.size, 36)
        assertEquals(eegTsvReader.facadeEnclosureList.size, 9)
        assertEquals(eegTsvReader.openSpaceSystemEegList.size, 9)
    }
}