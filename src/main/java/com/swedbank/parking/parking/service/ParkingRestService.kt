package com.swedbank.parking.parking.service

import com.swedbank.parking.parking.dto.ParkingDto
import com.swedbank.parking.parking.dto.ParkingUpsertRequest
import com.swedbank.parking.parking.mapper.ParkingMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ParkingRestService(
    private val parkingService: ParkingService,
    private val parkingMapper: ParkingMapper,
) {
    @Transactional(readOnly = true)
    fun getByUid(uid: UUID): ParkingDto {
        return parkingMapper.getParkingDto(
            parking = parkingService.getByUidFetchingFloorsAndLotsNN(uid),
            withFloors = true,
        )
    }

    @Transactional
    fun save(request: ParkingUpsertRequest): ParkingDto {
        val parking = parkingService.saveOrUpdate(
            parkingMapper.getParking(request),
        )
        return parkingMapper.getParkingDto(parking)
    }

    @Transactional
    fun update(uid: UUID, request: ParkingUpsertRequest): ParkingDto {
        val parking = parkingService.getByUidLockedNN(uid)
        return parkingMapper.getParkingDto(
            parking = parkingService.saveOrUpdate(
                parkingMapper.mergeParking(
                    parking = parking,
                    request = request,
                ),
            ),
        )
    }

    @Transactional(readOnly = true)
    fun getAll() = parkingService.getAllFetchingAll()
        .map {
            parkingMapper.getParkingDto(
                parking = it,
                withFloors = true,
            )
        }
}
