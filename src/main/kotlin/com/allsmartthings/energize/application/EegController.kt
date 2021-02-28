package com.allsmartthings.energize.application

import com.allsmartthings.energize.domain.EegService
import io.github.bucket4j.Bucket
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
class EegController(val eegService: EegService, val bucket: Bucket) {

    @GetMapping
    fun getEeg(@RequestParam date: String, @RequestParam kwp: Double): ResponseEntity<EegDto> {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(EegDto.fromDomain(eegService.getEegByYearMonthAndKwp(YearMonth.parse(date), BigDecimal.valueOf(kwp))));
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build()
    }
}