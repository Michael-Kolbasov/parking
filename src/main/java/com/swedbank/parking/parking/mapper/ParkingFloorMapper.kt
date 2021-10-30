package com.swedbank.parking.parking.mapper

import com.swedbank.parking.parking.dto.ParkingFloorDto
import com.swedbank.parking.parking.dto.ParkingFloorUpsertRequest
import com.swedbank.parking.parking.model.ParkingFloor
import com.swedbank.parking.parking.service.ParkingService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Component
class ParkingFloorMapper(
    private val parkingLotMapper: ParkingLotMapper,
    private val parkingService: ParkingService,
) {
    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    fun getParkingFloor(request: ParkingFloorUpsertRequest): ParkingFloor = with(request) {
        ParkingFloor(
            name = name!!.trim(),
            height = height!!,
            weightCapacity = weightCapacity!!,
            parking = parkingService.getByUidNN(request.parkingUid),
        )
    }

    fun merge(parkingFloor: ParkingFloor, request: ParkingFloorUpsertRequest): ParkingFloor =
        parkingFloor.apply {
            name = request.name!!.trim()
            height = request.height!!
            weightCapacity = request.weightCapacity!!
            updated = Instant.now()
        }
}
