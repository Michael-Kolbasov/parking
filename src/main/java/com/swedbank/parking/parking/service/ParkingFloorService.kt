package com.swedbank.parking.parking.service

import com.swedbank.parking.common.exception.NotFoundException
import com.swedbank.parking.parking.dao.ParkingFloorRepository
import com.swedbank.parking.parking.model.ParkingFloor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ParkingFloorService(
    private val parkingFloorRepository: ParkingFloorRepository,
) {
    @Transactional(readOnly = true)
    fun getByUidNN(uid: UUID): ParkingFloor {
        return parkingFloorRepository.findByUid(uid)
            ?: throw NotFoundException("Not found parking floor by uid $uid")
    }

    @Transactional(readOnly = true)
    fun getByUidLockedNN(uid: UUID): ParkingFloor {
        return parkingFloorRepository.findByUidLocked(uid)
            ?: throw NotFoundException("Not found parking floor by uid $uid")
    }

    @Transactional(readOnly = true)
    fun getByUidFetchingAllNN(uid: UUID): ParkingFloor {
        return parkingFloorRepository.findByUidFetchingAll(uid)
            ?: throw NotFoundException("Not found parking floor by uid $uid")
    }

    @Transactional
    fun saveOrUpdate(parkingFloor: ParkingFloor): ParkingFloor {
        return parkingFloorRepository.saveAndFlush(parkingFloor)
    }

    @Transactional(readOnly = true)
    fun getAll(): Collection<ParkingFloor> {
        return parkingFloorRepository.findAllFetchingAll()
    }
}
