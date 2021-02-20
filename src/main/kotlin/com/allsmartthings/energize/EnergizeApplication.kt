package com.allsmartthings.energize

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EnergizeApplication

fun main(args: Array<String>) {
    runApplication<EnergizeApplication>(*args)
}
