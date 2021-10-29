package com.swedbank.parking.parking.dao

import com.swedbank.parking.parking.model.Vehicle
import org.springframework.data.jpa.repository.JpaRepository

interface VehicleRepository : JpaRepository<Vehicle, Long> {
}
