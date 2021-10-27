package com.swedbank.parking.parking.service

import com.swedbank.parking.parking.dao.ParkingTicketRepository
import com.swedbank.parking.parking.model.ParkingTicket
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ParkingTicketService(
    private val parkingTicketRepository: ParkingTicketRepository,
) {
    @Transactional
    fun saveOrUpdate(ticket: ParkingTicket): ParkingTicket {
        return parkingTicketRepository.saveAndFlush(ticket)
    }
}
