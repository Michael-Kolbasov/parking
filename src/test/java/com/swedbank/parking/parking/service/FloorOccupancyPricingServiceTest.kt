package com.swedbank.parking.parking.service

import com.swedbank.parking.AbstractIntegrationTest
import com.swedbank.parking.parking.config.ParkingProperties
import com.swedbank.parking.parking.model.ParkingFloor
import com.swedbank.parking.parking.model.ParkingLot
import com.swedbank.parking.parking.model.Vehicle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal

class FloorOccupancyPricingServiceTest : AbstractIntegrationTest() {
    @Autowired
    private lateinit var floorOccupancyPricingService: FloorOccupancyPricingService
    @Autowired
    private lateinit var parkingProperties: ParkingProperties

    @Test
    fun calculatePrice_success() {
        val occupiedLots = 10
        val freeLots = 10
        val createLotAtFloorWithVehicle: (ParkingFloor, Vehicle?) -> ParkingLot = { floor, vehicle ->
            testUtils.createRandomParkingLot(
                customizer = {
                    it.floor = floor
                    it.occupiedBy = vehicle
                }
            )
        }
        val floor = testUtils.createRandomParkingFloor()
            .also {
                val lots = mutableListOf<ParkingLot>()
                repeat(occupiedLots) { _ ->
                    lots.add(createLotAtFloorWithVehicle(it, testUtils.createRandomVehicle()))
                }
                repeat(freeLots) { _ ->
                    lots.add(createLotAtFloorWithVehicle(it, null))
                }
                it.lots.addAll(lots)
            }
        val freeLot = floor.lots.find { it.occupiedBy == null }!!
        val ticket = testUtils.createRandomParkingTicket {
            it.lot = freeLot
        }

        val basePrice = parkingProperties.ticket.price.base!!
        val expected = basePrice + (basePrice * BigDecimal.valueOf(0.5))
        val price = floorOccupancyPricingService.calculatePrice(ticket)
        assertEquals(expected, price)
    }
}
