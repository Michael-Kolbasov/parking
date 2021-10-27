package com.swedbank.parking.parking.service

import com.swedbank.parking.parking.dao.ParkingLotRepository
import com.swedbank.parking.parking.model.ParkingLot
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ParkingLotService(
    private val parkingLotRepository: ParkingLotRepository,
) {
    @Transactional
    fun saveOrUpdate(lot: ParkingLot): ParkingLot {
        return parkingLotRepository.saveAndFlush(lot)
    }
}
