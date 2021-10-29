package com.swedbank.parking.parking.mapper

import com.swedbank.parking.ParkingApplication
import com.swedbank.parking.parking.dto.ParkingTicketDto
import com.swedbank.parking.parking.model.ParkingLot
import com.swedbank.parking.parking.model.ParkingTicket
import com.swedbank.parking.parking.model.PricingStrategy
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime

@Component
class ParkingTicketMapper {
    fun getTicketDto(
        ticket: ParkingTicket,
        pricePerMinute: BigDecimal,
        pricingStrategy: PricingStrategy,
    ) = with(ticket) {
        ParkingTicketDto(
            uid = uid,
            pricePerMinute = pricePerMinute,
            pricingStrategy = pricingStrategy.toApi(),
            created = LocalDateTime.ofInstant(Instant.now(), ParkingApplication.utc),
            paid = paid,
            paidAt = paidAt?.let { LocalDateTime.ofInstant(paidAt, ParkingApplication.utc) },
            lot = with(lot) {
                ParkingTicketDto.ParkingLotDto(
                    uid = uid,
                    name = name,
                    floor = with(floor) {
                        ParkingTicketDto.ParkingLotDto.ParkingFloorDto(
                            uid = uid,
                            name = name,
                            parking = with (parking) {
                                ParkingTicketDto.ParkingLotDto.ParkingFloorDto.ParkingDto(
                                    uid = uid,
                                    name = name,
                                )
                            }
                        )
                    }
                )
            }
        )
    }

    fun getTicket(lot: ParkingLot) = ParkingTicket(
        lot = lot,
    )
}
