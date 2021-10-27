package com.swedbank.parking.parking.dao

import com.swedbank.parking.AbstractIntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ParkingTicketRepositoryTest : AbstractIntegrationTest() {
    @Autowired
    private lateinit var parkingTicketRepository: ParkingTicketRepository

    @Test
    fun findByUid_success() {
        val ticket = testUtils.createRandomParkingTicket()
        assertEquals(ticket, parkingTicketRepository.findByUid(ticket.uid))
    }
}
