package com.swedbank.parking.parking.controller

import com.swedbank.parking.parking.dto.ApiPricingStrategy
import com.swedbank.parking.parking.dto.ParkingTicketCreateRequest
import com.swedbank.parking.parking.dto.ParkingTicketDto
import com.swedbank.parking.parking.service.ParkingTicketRestService
import org.springframework.validation.annotation.Validated
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
) {
    @PostMapping(path = ["/{parkingUid}"])
    fun assign(
        @PathVariable parkingUid: UUID,
        @RequestParam(required = false) strategy: ApiPricingStrategy?,
        @[RequestBody Valid] request: ParkingTicketCreateRequest,
    ): ParkingTicketDto {
        return parkingTicketRestService.assign(
            parkingUid = parkingUid,
            request = request,
            strategy = strategy ?: defaultPricingStrategy,
        )
    }

    companion object {
        // todo move to properties
        private val defaultPricingStrategy = ApiPricingStrategy.FLOOR_OCCUPANCY
    }
}
