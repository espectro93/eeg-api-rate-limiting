package com.allsmartthings.energize.infrastructure.config

import com.allsmartthings.energize.infrastructure.interceptors.RateLimitInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


class AppConfig(private val rateLimitInterceptor: RateLimitInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns("/eeg")
    }
}