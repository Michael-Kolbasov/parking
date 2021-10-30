package com.swedbank.parking.parking.mapper

import com.swedbank.parking.parking.dto.ParkingLotDto
import com.swedbank.parking.parking.dto.ParkingLotUpsertRequest
import com.swedbank.parking.parking.model.ParkingLot
import com.swedbank.parking.parking.service.ParkingFloorService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Component
class ParkingLotMapper(
    private val parkingFloorService: ParkingFloorService,
) {
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

    @Transactional(readOnly = true)
    fun getParking(request: ParkingLotUpsertRequest): ParkingLot = with(request) {
        ParkingLot(
            name = request.name!!,
            floor = parkingFloorService.getByUidNN(parkingFloorUid!!),
        )
    }

    fun mergeLot(lot: ParkingLot, request: ParkingLotUpsertRequest): ParkingLot = lot.apply {
        name = request.name!!
        updated = Instant.now()
    }
}
