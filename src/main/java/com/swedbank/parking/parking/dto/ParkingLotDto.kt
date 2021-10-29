package com.swedbank.parking.parking.dto

import java.time.LocalDateTime
import java.util.UUID

data class ParkingLotDto(
    val uid: UUID,
    val name: String,
    val created: LocalDateTime,
    val updated: LocalDateTime,
    val occupied: Boolean,
    val floor: ParkingFloorDto,
) {
    data class ParkingFloorDto(
        val uid: UUID,
    )
}
