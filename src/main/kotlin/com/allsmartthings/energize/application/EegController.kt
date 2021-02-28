package com.allsmartthings.energize.application

import com.allsmartthings.energize.domain.EegService
import io.github.bucket4j.Bucket
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.time.YearMonth

@RestController
@Validated
@RequestMapping("/eeg")
class EegController(val eegService: EegService, val bucket: Bucket) {

    @GetMapping
    fun getEeg(@RequestHeader("x-api-key") apiKey: String,
               @RequestParam date: String,
               @RequestParam kwp: Double): EegDto {
        return EegDto.fromDomain(eegService.getEegByYearMonthAndKwp(YearMonth.parse(date), BigDecimal.valueOf(kwp)));
    }
}
