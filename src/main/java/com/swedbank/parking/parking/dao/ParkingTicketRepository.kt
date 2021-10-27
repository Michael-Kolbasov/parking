package com.swedbank.parking.parking.dao

import com.swedbank.parking.parking.model.ParkingTicket
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ParkingTicketRepository : JpaRepository<ParkingTicket, Long> {
    fun findByUid(uid: UUID): ParkingTicket?
}
