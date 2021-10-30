package com.swedbank.parking.parking.dao

import com.swedbank.parking.parking.model.ParkingLot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import javax.persistence.LockModeType

@Transactional(propagation = Propagation.MANDATORY)
interface ParkingLotRepository : JpaRepository<ParkingLot, Long> {
    fun findByUid(uid: UUID): ParkingLot?

    @Query("select pl from ParkingLot pl " +
            "where pl.uid = :uid")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByUidLocked(uid: UUID): ParkingLot?
}
