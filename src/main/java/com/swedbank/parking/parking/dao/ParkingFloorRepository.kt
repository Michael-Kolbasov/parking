package com.swedbank.parking.parking.dao

import com.swedbank.parking.parking.model.ParkingFloor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional(propagation = Propagation.MANDATORY)
interface ParkingFloorRepository : JpaRepository<ParkingFloor, Long> {
    fun findByUid(uid: UUID): ParkingFloor?
}
