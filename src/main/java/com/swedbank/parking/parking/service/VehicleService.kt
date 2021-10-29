package com.swedbank.parking.parking.service

import com.swedbank.parking.parking.dao.VehicleRepository
import com.swedbank.parking.parking.model.Vehicle
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class VehicleService(
    private val vehicleRepository: VehicleRepository,
) {
    @Transactional
    fun saveOrUpdate(vehicle: Vehicle): Vehicle {
        return vehicleRepository.saveAndFlush(vehicle)
    }
}
