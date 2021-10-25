package com.swedbank.parking.parking.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/parking"])
class ParkingController {
    @GetMapping
    fun getParking(): String {
        return "launches"
    }
}
