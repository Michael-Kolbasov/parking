package com.swedbank.parking.parking.service

import com.swedbank.parking.parking.dto.ParkingFloorDto
import com.swedbank.parking.parking.dto.ParkingFloorUpsertRequest
import com.swedbank.parking.parking.mapper.ParkingFloorMapper
import com.swedbank.parking.parking.validation.ParkingFloorValidator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ParkingFloorRestService(
    private val parkingFloorService: ParkingFloorService,
    private val parkingFloorMapper: ParkingFloorMapper,
) {
    @Transactional(readOnly = true)
    fun getByUid(uid: UUID): ParkingFloorDto {
        return parkingFloorMapper.getParkingFloorDto(
            floor = parkingFloorService.getByUidFetchingAllNN(uid),
            withLots = true,
        )
    }

    @Transactional
    fun save(request: ParkingFloorUpsertRequest): ParkingFloorDto {
        return parkingFloorMapper.getParkingFloorDto(
            floor = parkingFloorService.saveOrUpdate(
                parkingFloor = parkingFloorMapper.getParkingFloor(request),
            ),
        )
    }

    @Transactional
    fun update(uid: UUID, request: ParkingFloorUpsertRequest): ParkingFloorDto {
        parkingFloorService.getByUidLockedNN(uid)
        val parkingFloor = parkingFloorService.getByUidFetchingAllNN(uid)
        with(ParkingFloorValidator) {
            validateHeightChange(parkingFloor, request.height!!)
            validateWeightCapacityChange(parkingFloor, request.weightCapacity!!)
        }
        return parkingFloorMapper.getParkingFloorDto(
            floor = parkingFloorService.saveOrUpdate(
                parkingFloor = parkingFloorMapper.merge(parkingFloor, request),
            ),
        )
    }

    @Transactional(readOnly = true)
    fun getAll() = parkingFloorService.getAll()
        .map {
            parkingFloorMapper.getParkingFloorDto(
                floor = it,
                withLots = true,
            )
        }
}
