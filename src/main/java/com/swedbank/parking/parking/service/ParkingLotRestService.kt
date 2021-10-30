package com.swedbank.parking.parking.service

import com.swedbank.parking.parking.dto.ParkingLotDto
import com.swedbank.parking.parking.dto.ParkingLotUpsertRequest
import com.swedbank.parking.parking.mapper.ParkingLotMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ParkingLotRestService(
    private val parkingLotService: ParkingLotService,
    private val parkingLotMapper: ParkingLotMapper,
) {
    @Transactional(readOnly = true)
    fun getByUid(uid: UUID): ParkingLotDto {
        return parkingLotMapper.getParkingLotDto(
            lot = parkingLotService.getByUidNN(uid),
        )
    }

    @Transactional
    fun save(request: ParkingLotUpsertRequest): ParkingLotDto {
        return parkingLotMapper.getParkingLotDto(
            lot = parkingLotService.saveOrUpdate(
                lot = parkingLotMapper.getParking(request),
            ),
        )
    }

    @Transactional
    fun update(uid: UUID, request: ParkingLotUpsertRequest): ParkingLotDto {
        val lot = parkingLotService.getByUidLockedNN(uid)
        return parkingLotMapper.getParkingLotDto(
            lot = parkingLotService.saveOrUpdate(
                parkingLotMapper.mergeLot(
                    lot = lot,
                    request = request,
                ),
            ),
        )
    }

    @Transactional(readOnly = true)
    fun getAll() = parkingLotService.getAll()
        .map { parkingLotMapper.getParkingLotDto(lot = it) }
}
