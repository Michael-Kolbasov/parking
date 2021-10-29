package com.swedbank.parking.parking.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class ParkingFloorDto(
    val uid: UUID,
    val name: String,
    val height: BigDecimal,
    val weightCapacity: BigDecimal,
    val created: LocalDateTime,
    val updated: LocalDateTime,
    val parking: ParkingDto,
    val lots: List<ParkingLotDto> = listOf(),
) {
    data class ParkingDto(
        val uid: UUID,
    )
}
