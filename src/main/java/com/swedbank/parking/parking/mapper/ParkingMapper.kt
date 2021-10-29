package com.swedbank.parking.parking.mapper

import com.swedbank.parking.parking.dto.ParkingDto
import com.swedbank.parking.parking.model.Parking
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneOffset

@Component
class ParkingMapper(
    private val parkingFloorMapper: ParkingFloorMapper,
) {
    fun getParkingDto(
        parking: Parking,
        withFloors: Boolean = false,
    ) = with(parking) {
        ParkingDto(
            uid = uid,
            name = name,
            created = LocalDateTime.ofInstant(created, ZoneOffset.UTC),
            updated = LocalDateTime.ofInstant(updated, ZoneOffset.UTC),
            floors = if (withFloors) floors.map {
                parkingFloorMapper.getParkingFloorDto(
                    floor = it,
                    withLots = true,
                )
            } else listOf(),
        )
    }
}
