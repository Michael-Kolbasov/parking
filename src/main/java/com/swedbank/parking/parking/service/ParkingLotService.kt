package com.swedbank.parking.parking.service

import com.swedbank.parking.common.exception.NotFoundException
import com.swedbank.parking.parking.dao.ParkingLotRepository
import com.swedbank.parking.parking.model.ParkingLot
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ParkingLotService(
    private val parkingLotRepository: ParkingLotRepository,
) {
    @Transactional
    fun saveOrUpdate(lot: ParkingLot): ParkingLot {
        return parkingLotRepository.saveAndFlush(lot)
    }

    @Transactional(readOnly = true)
    fun getByUidNN(uid: UUID): ParkingLot {
        return parkingLotRepository.findByUid(uid)
            ?: throw NotFoundException("Not found parking lot by uid $uid.")
    }

    @Transactional(readOnly = true)
    fun getByUidLockedNN(uid: UUID): ParkingLot {
        return parkingLotRepository.findByUidLocked(uid)
            ?: throw NotFoundException("Not found parking lot by uid $uid.")
    }

    @Transactional(readOnly = true)
    fun getAll(): Collection<ParkingLot> = parkingLotRepository.findAll()
}
