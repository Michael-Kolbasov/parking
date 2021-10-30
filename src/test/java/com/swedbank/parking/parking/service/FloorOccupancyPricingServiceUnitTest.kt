package com.swedbank.parking.parking.service

import com.swedbank.parking.common.util.TestUtils
import com.swedbank.parking.parking.config.ParkingProperties
import com.swedbank.parking.parking.model.ParkingLot
import com.swedbank.parking.parking.model.ParkingTicket
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class FloorOccupancyPricingServiceUnitTest {
    private val parkingProperties = mockk<ParkingProperties>()
    private val floorOccupancyPricingService = FloorOccupancyPricingService(parkingProperties)

    @Test
    fun calculatePrice_success() {
        every { parkingProperties.ticket.price.base } returns BigDecimal.valueOf(10.0)
        val generateParkingLot: (Boolean) -> ParkingLot = { withVehicle ->
            TestUtils.getRandomParkingLot()
                .also {
                    if (withVehicle) {
                        it.occupiedBy = TestUtils.getRandomVehicle()
                    }
                }
        }
        val ticket = mockk<ParkingTicket> {
            every { lot } returns mockk {
                every { floor } returns mockk {
                    every { lots } returns List(
                        size = 20,
                        init = { if (it < 10) generateParkingLot(true) else generateParkingLot(false) }
                    ).toMutableSet()
                }
            }
        }
        val expected = BigDecimal("15.00")
        assertEquals(expected, floorOccupancyPricingService.calculatePrice(ticket))
    }
}
