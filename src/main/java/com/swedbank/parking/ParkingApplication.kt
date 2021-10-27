package com.swedbank.parking

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import java.nio.charset.Charset
import java.time.ZoneId
import java.util.Locale

@SpringBootApplication
@ConfigurationPropertiesScan
class ParkingApplication {
    companion object {
        val utc: ZoneId = ZoneId.of("UTC")
        val defaultEncoding: Charset = Charsets.UTF_8
        val defaultLocale: Locale = Locale.ENGLISH
        const val messageSourceName = "messages"
    }
}

fun main(args: Array<String>) {
    runApplication<ParkingApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
