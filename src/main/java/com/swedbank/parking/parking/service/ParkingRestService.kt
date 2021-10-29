package com.swedbank.parking.parking.service

import com.swedbank.parking.parking.dto.ParkingDto
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
}
