package com.swedbank.parking.parking.service

import com.swedbank.parking.common.exception.NotFoundException
import com.swedbank.parking.parking.dao.ParkingTicketRepository
import com.swedbank.parking.parking.model.ParkingTicket
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ParkingTicketService(
    private val parkingTicketRepository: ParkingTicketRepository,
) {
    @Transactional
    fun saveOrUpdate(ticket: ParkingTicket): ParkingTicket {
        return parkingTicketRepository.saveAndFlush(ticket)
    }

    @Transactional(readOnly = true)
    fun getByUidNN(uid: UUID): ParkingTicket {
        return parkingTicketRepository.findByUid(uid)
            ?: throw NotFoundException("Not found parking ticket by uid $uid")
    }

    @Transactional(readOnly = true)
    fun getAllFetchingAll(): Collection<ParkingTicket> = parkingTicketRepository.findAllFetchingAll()
}
