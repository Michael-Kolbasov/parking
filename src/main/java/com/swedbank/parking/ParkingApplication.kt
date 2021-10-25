package com.swedbank.parking

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ParkingApplication

fun main(args: Array<String>) {
    runApplication<ParkingApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
