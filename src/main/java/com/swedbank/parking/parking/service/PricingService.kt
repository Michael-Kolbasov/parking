package com.swedbank.parking.parking.service

import com.swedbank.parking.parking.model.ParkingTicket
import com.swedbank.parking.parking.model.PricingStrategy
import java.math.BigDecimal

interface PricingService {
    val pricingStrategy: PricingStrategy
    fun calculatePrice(ticket: ParkingTicket): BigDecimal
}
