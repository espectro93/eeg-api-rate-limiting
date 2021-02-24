package com.allsmartthings.energize.infrastructure.clients.tsveeg.v1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class EegV1ReaderTest{

    private val eegTsvReader: EegV1Reader = EegV1Reader()

    @Test
    fun readEegTill2008(){
        assertEquals(eegTsvReader.eegByYearList.size, 36)
        assertEquals(eegTsvReader.facadeEnclosureList.size, 9)
        assertEquals(eegTsvReader.openSpaceSystemEegList.size, 9)
    }
}