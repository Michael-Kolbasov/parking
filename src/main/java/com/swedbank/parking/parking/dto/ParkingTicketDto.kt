package com.swedbank.parking.parking.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class ParkingTicketDto(
    val uid: UUID,
    val pricePerMinute: BigDecimal?,
    val pricingStrategy: ApiPricingStrategy?,
    val created: LocalDateTime,
    val paid: Boolean,
    val paidAt: LocalDateTime? = null,
    val lot: ParkingLotDto,
) {
    data class ParkingLotDto(
        val uid: UUID,
        val name: String,
        val floor: ParkingFloorDto,
    ) {
        data class ParkingFloorDto(
            val uid: UUID,
            val name: String,
            val parking: ParkingDto,
        ) {
            data class ParkingDto(
                val uid: UUID,
                val name: String,
            )
        }
    }
}
