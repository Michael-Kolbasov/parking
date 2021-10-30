package com.swedbank.parking.parking.service

import com.swedbank.parking.common.util.TestUtils
import com.swedbank.parking.parking.config.ParkingProperties
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DefaultPricingServiceUnitTest {
    private val parkingProperties = mockk<ParkingProperties>()
    private val defaultPricingService = DefaultPricingService(parkingProperties)

    @Test
    fun calculatePrice_success() {
        val expected = BigDecimal.valueOf(10.0)
        every { parkingProperties.ticket.price.base } returns expected
        assertEquals(expected, defaultPricingService.calculatePrice(TestUtils.getRandomParkingTicket()))
    }
}
