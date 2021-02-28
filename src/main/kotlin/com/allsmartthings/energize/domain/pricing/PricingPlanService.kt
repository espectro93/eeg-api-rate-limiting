package com.allsmartthings.energize.domain.pricing

import io.github.bucket4j.Bucket
import io.github.bucket4j.Bucket4j
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class PricingPlanService {
    private val cache: MutableMap<String, Bucket> = ConcurrentHashMap()

    fun resolveBucket(apiKey: String): Bucket {
        return cache.computeIfAbsent(apiKey) { apiKey: String -> newBucket(apiKey) }
    }

    private fun newBucket(apiKey: String): Bucket {
        val pricingPlan: PricingPlan = PricingPlan.resolvePlanFromApiKey(apiKey)
        return Bucket4j.builder()
                .addLimit(PricingPlan.getLimit(pricingPlan))
                .build()
    }
}