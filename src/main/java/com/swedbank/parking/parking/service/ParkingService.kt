package com.swedbank.parking.parking.service

import com.swedbank.parking.common.exception.NotFoundException
import com.swedbank.parking.parking.dao.ParkingRepository
import com.swedbank.parking.parking.model.Parking
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ParkingService(
    private val parkingRepository: ParkingRepository,
) {
    @Transactional
    fun saveOrUpdate(parking: Parking): Parking {
        return parkingRepository.saveAndFlush(parking)
    }

    @Transactional(readOnly = true)
    fun getByUidNN(uid: UUID): Parking {
        return parkingRepository.findByUid(uid)
            ?: throw NotFoundException("Not found parking by uid $uid")
    }

    @Transactional(readOnly = true)
    fun getByUidLockedNN(uid: UUID): Parking {
        return parkingRepository.findByUidFetchingAll(uid)
            ?: throw NotFoundException("Not found parking by uid $uid")
    }

    @Transactional(readOnly = true)
    fun getByUidFetchingFloorsAndLotsNN(uid: UUID): Parking {
        return parkingRepository.findByUidFetchingAll(uid)
            ?: throw NotFoundException("Not found parking by uid $uid")
    }

    @Transactional(readOnly = true)
    fun getAllFetchingAll(): Collection<Parking> = parkingRepository.getAllFetchingAll()
}
