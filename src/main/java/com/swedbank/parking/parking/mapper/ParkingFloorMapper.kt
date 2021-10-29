package com.swedbank.parking.parking.mapper

import com.swedbank.parking.parking.dto.ParkingFloorDto
import com.swedbank.parking.parking.model.ParkingFloor
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneOffset

@Component
class ParkingFloorMapper(
    private val parkingLotMapper: ParkingLotMapper
) {
    fun getParkingFloorDto(
        floor: ParkingFloor,
        withLots: Boolean = false,
    ): ParkingFloorDto = with(floor) {
        ParkingFloorDto(
            uid = uid,
            name = name,
            height = height,
            weightCapacity = weightCapacity,
            created = LocalDateTime.ofInstant(created, ZoneOffset.UTC),
            updated = LocalDateTime.ofInstant(updated, ZoneOffset.UTC),
            parking = ParkingFloorDto.ParkingDto(
                uid = parking.uid,
            ),
            lots = if (withLots) lots.map(parkingLotMapper::getParkingLotDto) else listOf(),
        )
    }
}
