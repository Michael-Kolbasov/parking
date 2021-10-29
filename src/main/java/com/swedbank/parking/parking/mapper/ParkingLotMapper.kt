package com.swedbank.parking.parking.mapper

import com.swedbank.parking.parking.dto.ParkingLotDto
import com.swedbank.parking.parking.model.ParkingLot
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneOffset

@Component
class ParkingLotMapper {
    fun getParkingLotDto(lot: ParkingLot): ParkingLotDto = with(lot) {
        ParkingLotDto(
            uid = uid,
            name = name,
            created = LocalDateTime.ofInstant(created, ZoneOffset.UTC),
            updated = LocalDateTime.ofInstant(updated, ZoneOffset.UTC),
            occupied = occupiedBy != null,
            floor = ParkingLotDto.ParkingFloorDto(
                uid = floor.uid,
            ),
        )
    }
}
