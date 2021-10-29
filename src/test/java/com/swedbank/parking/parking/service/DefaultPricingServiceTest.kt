package com.swedbank.parking.parking.service

import com.swedbank.parking.AbstractIntegrationTest
import com.swedbank.parking.parking.config.ParkingProperties
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class DefaultPricingServiceTest : AbstractIntegrationTest() {
    @Autowired
    private lateinit var defaultPricingService: DefaultPricingService
    @Autowired
    private lateinit var parkingProperties: ParkingProperties

    @Test
    fun calculatePrice_success() {
        val ticket = testUtils.createRandomParkingTicket()
        assertEquals(parkingProperties.ticket.price.base!!, defaultPricingService.calculatePrice(ticket))
    }
}
