package com.allsmartthings.energize.application

import com.allsmartthings.energize.domain.EegService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.time.YearMonth

@RestController
@Validated
@RequestMapping("/eeg")
class EegController(val eegService: EegService) {

    @GetMapping
    fun getEeg(@RequestParam date: String, @RequestParam kwp: Double): EegDto {
        return EegDto.fromDomain(eegService.getEegByYearMonthAndKwp(YearMonth.parse(date), BigDecimal.valueOf(kwp)));
    }
}