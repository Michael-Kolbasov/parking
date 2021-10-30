package com.swedbank.parking.parking.dao

import com.swedbank.parking.parking.model.ParkingFloor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import javax.persistence.LockModeType

@Transactional(propagation = Propagation.MANDATORY)
interface ParkingFloorRepository : JpaRepository<ParkingFloor, Long> {
    fun findByUid(uid: UUID): ParkingFloor?

    @Query("select pf from ParkingFloor pf " +
            "where pf.uid = :uid")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByUidLocked(uid: UUID): ParkingFloor?

    @Query("select distinct pf from ParkingFloor pf " +
            "left join fetch pf.lots pfl " +
            "left join fetch pfl.occupiedBy " +
            "where pf.uid = :uid")
    fun findByUidFetchingAll(uid: UUID): ParkingFloor?

    @Query("select distinct pf from ParkingFloor pf " +
            "left join fetch pf.lots pfl " +
            "left join fetch pfl.occupiedBy")
    fun findAllFetchingAll(): Set<ParkingFloor>
}
