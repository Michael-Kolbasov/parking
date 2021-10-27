package com.swedbank.parking.parking.service

import com.swedbank.parking.parking.config.ParkingProperties
import com.swedbank.parking.parking.model.ParkingTicket
import com.swedbank.parking.parking.model.PricingStrategy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

@Service
class FloorOccupancyPricingService(
    private val parkingProperties: ParkingProperties,
) : PricingService {
    override val pricingStrategy = PricingStrategy.FLOOR_OCCUPANCY

    @Transactional
    override fun calculatePrice(ticket: ParkingTicket): BigDecimal {
        val floor = ticket.lot.floor
        val occupiedLotsAmount = floor.lots.count { it.occupiedBy != null }
        val totalLotsAmount = floor.lots.size
        val floorOccupancyPercent = (occupiedLotsAmount / totalLotsAmount).toBigDecimal()
        val avgPrice = parkingProperties.ticket.price.base!!
        val price = avgPrice + (avgPrice * floorOccupancyPercent)
        return price.round(roundToHalfDown)
    }

    companion object {
        private val roundToHalfDown = MathContext(1, RoundingMode.HALF_DOWN)
    }
}
