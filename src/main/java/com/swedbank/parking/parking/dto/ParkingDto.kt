package com.swedbank.parking.parking.dto

import java.time.LocalDateTime
import java.util.UUID

data class ParkingDto(
    val uid: UUID,
    val name: String,
    val created: LocalDateTime,
    val updated: LocalDateTime,
)
