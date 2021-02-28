package com.allsmartthings.energize.infrastructure.clients.tsveeg

import com.allsmartthings.energize.domain.Eeg
import com.allsmartthings.energize.domain.EegValue
import com.allsmartthings.energize.domain.EegService
import com.allsmartthings.energize.infrastructure.clients.tsveeg.v1.EegV1
import com.allsmartthings.energize.infrastructure.clients.tsveeg.v1.EegV1Reader
import com.allsmartthings.energize.infrastructure.clients.tsveeg.v2.EegV2
import com.allsmartthings.energize.infrastructure.clients.tsveeg.v2.EegV2Reader
import com.allsmartthings.energize.infrastructure.clients.tsveeg.v3.EegV3
import com.allsmartthings.energize.infrastructure.clients.tsveeg.v3.EegV3Reader
import com.allsmartthings.energize.infrastructure.clients.tsveeg.v4.EegV4
import com.allsmartthings.energize.infrastructure.clients.tsveeg.v4.EegV4Reader
import com.allsmartthings.energize.infrastructure.clients.tsveeg.v5.EegV5
import com.allsmartthings.energize.infrastructure.clients.tsveeg.v5.EegV5Reader
import org.springframework.context.support.beans
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.YearMonth

@Service
class EegFacade(
    eegV1Reader: EegV1Reader, eegV2Reader: EegV2Reader,
    eegV3Reader: EegV3Reader, eegV4Reader: EegV4Reader, eegV5Reader: EegV5Reader
) : EegService {

    private val eegV1: EegV1 = eegV1Reader.readFromTsv()
    private val eegV2: EegV2 = eegV2Reader.readFromTsv()
    private val eegV3: EegV3 = eegV3Reader.readFromTsv()
    private val eegV4: EegV4 = eegV4Reader.readFromTsv()
    private val eegV5: EegV5 = eegV5Reader.readFromTsv()

    //TODO: CIRCUIT BREAKER J4F & SELENIUM CRAWLER FOR EPEX SPOT

    override fun getEegByYearMonthAndKwp(yearMonth: YearMonth, kwp: BigDecimal): Eeg {
        TODO("Not yet implemented")
    }

    private fun determineEegType(yearMonth: YearMonth): EegMarker {
        if (yearMonth.isBefore(YearMonth.of(2009, 1))) return eegV1;
        else if (yearMonth.isAfter(YearMonth.of(2008, 12)) && yearMonth.isBefore(YearMonth.of(2012, 4))) return eegV2;
        else return eegV3
    }
}