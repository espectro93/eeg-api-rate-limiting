package com.allsmartthings.energize.domain.pricing

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Refill
import java.time.Duration


internal enum class PricingPlan {
    FREE,
    BASIC,
    PROFESSIONAL;

    companion object {
        fun resolvePlanFromApiKey(apiKey: String?): PricingPlan {
            if (apiKey == null || apiKey.isEmpty()) {
                return FREE
            } else if (apiKey.startsWith("PX001-")) {
                return PROFESSIONAL
            } else if (apiKey.startsWith("BX001-")) {
                return BASIC
            }
            return FREE
        }

        fun getLimit(pricingPlan: PricingPlan): Bandwidth {
            return when (pricingPlan) {
                FREE -> Bandwidth.classic(20, Refill.intervally(20, Duration.ofHours(1)));
                BASIC -> Bandwidth.classic(40, Refill.intervally(40, Duration.ofHours(1)));
                PROFESSIONAL -> Bandwidth.classic(100, Refill.intervally(100, Duration.ofHours(1)));
            }
        }
    }
}