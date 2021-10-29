package com.swedbank.parking.parking.dao

import com.swedbank.parking.parking.model.Parking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import javax.persistence.LockModeType

@Transactional(propagation = Propagation.MANDATORY)
interface ParkingRepository : JpaRepository<Parking, Long> {
    fun findByUid(uid: UUID): Parking?

    @Query("select p from Parking p " +
            "where p.uid = :uid")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByUidLocked(uid: UUID): Parking?

    @Query("select distinct p from Parking p " +
            "left join fetch p.floors pf " +
            "left join fetch pf.lots pfl " +
            "left join fetch pfl.occupiedBy pflv " +
            "where p.uid = :uid")
    fun findByUidFetchingAll(uid: UUID): Parking?
}
