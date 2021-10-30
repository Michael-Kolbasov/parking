package com.swedbank.parking.parking.controller

import com.swedbank.parking.parking.dto.ParkingDto
import com.swedbank.parking.parking.dto.ParkingUpsertRequest
import com.swedbank.parking.parking.service.ParkingRestService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/v1/parking"])
@Validated
class ParkingController(
    private val parkingRestService: ParkingRestService,
) {
    @GetMapping(path = ["/{uid}"])
    fun getByUid(@PathVariable uid: UUID): ParkingDto {
        return parkingRestService.getByUid(uid)
    }

    @PostMapping
    fun create(@[RequestBody Valid] request: ParkingUpsertRequest): ParkingDto {
        return parkingRestService.save(request)
    }

    @PutMapping(path = ["/{parkingUid}"])
    fun update(
        @PathVariable parkingUid: UUID,
        @[RequestBody Valid] request: ParkingUpsertRequest,
    ): ParkingDto {
        return parkingRestService.update(parkingUid, request)
    }

    @GetMapping
    fun getAll(): Collection<ParkingDto> = parkingRestService.getAll()
}
