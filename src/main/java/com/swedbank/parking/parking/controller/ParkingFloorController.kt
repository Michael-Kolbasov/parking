package com.swedbank.parking.parking.controller

import com.swedbank.parking.parking.dto.ParkingFloorDto
import com.swedbank.parking.parking.dto.ParkingFloorUpsertRequest
import com.swedbank.parking.parking.service.ParkingFloorRestService
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
@RequestMapping(path = ["/v1/parking/floor"])
@Validated
class ParkingFloorController(
    private val parkingFloorRestService: ParkingFloorRestService,
) {
    @GetMapping(path = ["/{floorUid}"])
    fun getByUid(@PathVariable floorUid: UUID): ParkingFloorDto {
        return parkingFloorRestService.getByUid(floorUid)
    }

    @PostMapping
    fun create(@[RequestBody Valid] request: ParkingFloorUpsertRequest): ParkingFloorDto {
        return parkingFloorRestService.save(request)
    }

    @PutMapping(path = ["/{floorUid}"])
    fun update(
        @PathVariable floorUid: UUID,
        @[RequestBody Valid] request: ParkingFloorUpsertRequest
    ): ParkingFloorDto {
        return parkingFloorRestService.update(floorUid, request)
    }

    @GetMapping
    fun getAll(): Collection<ParkingFloorDto> = parkingFloorRestService.getAll()
}
