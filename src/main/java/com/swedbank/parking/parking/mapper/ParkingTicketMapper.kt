package com.swedbank.parking.parking.mapper

import com.swedbank.parking.parking.dto.ParkingTicketDto
import com.swedbank.parking.parking.model.ParkingLot
import com.swedbank.parking.parking.model.ParkingTicket
import org.springframework.stereotype.Component

@Component
class ParkingTicketMapper {
    fun getTicketDto(ticket: ParkingTicket) = with(ticket) {
        ParkingTicketDto(
            uid = uid,
        )
    }

    fun getTicket(lot: ParkingLot) = ParkingTicket(
        lot = lot,
    )
}
