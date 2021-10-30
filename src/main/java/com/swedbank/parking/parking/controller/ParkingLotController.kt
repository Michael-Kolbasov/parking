package com.swedbank.parking.parking.controller

import com.swedbank.parking.parking.dto.ParkingLotDto
import com.swedbank.parking.parking.dto.ParkingLotUpsertRequest
import com.swedbank.parking.parking.service.ParkingLotRestService
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
@RequestMapping(path = ["/v1/parking/lot"])
@Validated
class ParkingLotController(
    private val parkingLotRestService: ParkingLotRestService,
) {
    @GetMapping(path = ["/{uid}"])
    fun getByUid(@PathVariable uid: UUID): ParkingLotDto {
        return parkingLotRestService.getByUid(uid)
    }

    @PostMapping
    fun create(@[RequestBody Valid] request: ParkingLotUpsertRequest): ParkingLotDto {
        return parkingLotRestService.save(request)
    }

    @PutMapping(path = ["/{parkingLotUid}"])
    fun update(
        @PathVariable parkingLotUid: UUID,
        @[RequestBody Valid] request: ParkingLotUpsertRequest,
    ): ParkingLotDto {
        return parkingLotRestService.update(parkingLotUid, request)
    }

    @GetMapping
    fun getAll(): Collection<ParkingLotDto> = parkingLotRestService.getAll()
}
