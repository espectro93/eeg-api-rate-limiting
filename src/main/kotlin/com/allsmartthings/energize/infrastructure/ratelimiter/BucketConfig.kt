package com.allsmartthings.energize.infrastructure.ratelimiter

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Bucket4j
import io.github.bucket4j.Refill
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration


@Configuration
class BucketConfig {
    @Bean
    fun bucket(): Bucket {
        val limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)))
        return Bucket4j.builder()
                .addLimit(limit)
                .build()
    }
}