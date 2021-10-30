package com.swedbank.parking.parking.controller

import com.swedbank.parking.parking.config.ParkingProperties
import com.swedbank.parking.parking.dto.ApiPricingStrategy
import com.swedbank.parking.parking.dto.ParkingTicketAssignRequest
import com.swedbank.parking.parking.dto.ParkingTicketDto
import com.swedbank.parking.parking.service.ParkingTicketRestService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/v1/parking/ticket"])
@Validated
class ParkingTicketController(
    private val parkingTicketRestService: ParkingTicketRestService,
    private val parkingProperties: ParkingProperties,
) {
    @PostMapping(path = ["/{parkingUid}"])
    fun assign(
        @PathVariable parkingUid: UUID,
        @RequestParam(required = false) strategy: ApiPricingStrategy?,
        @[RequestBody Valid] request: ParkingTicketAssignRequest,
    ): ParkingTicketDto {
        return parkingTicketRestService.assign(
            parkingUid = parkingUid,
            request = request,
            strategy = strategy ?: parkingProperties.ticket.price.strategy.default!!.toApi(),
        )
    }

    @GetMapping(path = ["/{ticketUid}"])
    fun getByUid(@PathVariable ticketUid: UUID): ParkingTicketDto {
        return parkingTicketRestService.getByUid(ticketUid)
    }

    @GetMapping
    fun getAll(): Collection<ParkingTicketDto> = parkingTicketRestService.getAll()
}
