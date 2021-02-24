package com.allsmartthings.energize.infrastructure.clients.tsveeg.v2


import junit.framework.Assert.assertEquals
import org.junit.Test


internal class EegV2ReaderTest{
    private val eegTsvReader: EegV2Reader = EegV2Reader()

    @Test
    fun readEegTillMarch2009(){
        assertEquals(eegTsvReader.getEeg().eegByYearMonthList.size, 36)
        assertEquals(eegTsvReader.getEeg().facadeEnclosureList.size, 9)
        assertEquals(eegTsvReader.getEeg().openSpaceSystemEegList.size, 9)
    }
}