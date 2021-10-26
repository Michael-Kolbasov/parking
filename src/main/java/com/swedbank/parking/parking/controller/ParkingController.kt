package com.swedbank.parking.parking.controller

import com.swedbank.parking.parking.dto.ParkingDto
import com.swedbank.parking.parking.service.ParkingRestService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(path = ["/v1/parking"])
class ParkingController(
    private val parkingRestService: ParkingRestService,
) {
    @GetMapping(path = ["/{uid}"])
    fun getByUid(@PathVariable uid: UUID): ParkingDto {
        return parkingRestService.getByUid(uid)
    }
}
