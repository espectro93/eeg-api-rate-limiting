package com.allsmartthings.energize

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class EegTsvReaderTest{

    private val eegTsvReader: EegTsvReader = EegTsvReader()

    @Test
    fun readEegTill2008(){
        eegTsvReader.readEeg()
    }
}