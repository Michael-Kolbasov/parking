package com.swedbank.parking.parking.service

import com.swedbank.parking.parking.config.ParkingProperties
import com.swedbank.parking.parking.model.ParkingTicket
import com.swedbank.parking.parking.model.PricingStrategy
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class DefaultPricingService(
    private val parkingProperties: ParkingProperties,
) : PricingService {
    override val pricingStrategy = PricingStrategy.DEFAULT

    override fun calculatePrice(ticket: ParkingTicket): BigDecimal {
        return parkingProperties.ticket.price.base!!
    }
}
