package com.swedbank.parking.parking.dao

import com.swedbank.parking.parking.model.ParkingTicket
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional(propagation = Propagation.MANDATORY)
interface ParkingTicketRepository : JpaRepository<ParkingTicket, Long> {
    fun findByUid(uid: UUID): ParkingTicket?

    @Query("select distinct pt from ParkingTicket pt " +
            "left join fetch pt.lot ptl " +
            "left join fetch ptl.floor ptlf " +
            "left join fetch ptlf.parking ")
    fun findAllFetchingAll(): Set<ParkingTicket>
}
