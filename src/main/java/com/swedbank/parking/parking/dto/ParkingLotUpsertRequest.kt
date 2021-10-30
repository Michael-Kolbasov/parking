package com.swedbank.parking.parking.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.UUID
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@JsonIgnoreProperties(ignoreUnknown = true)
data class ParkingLotUpsertRequest(
    @field:[NotBlank Size(min = 1)]
    val name: String? = null,
    @field:NotNull
    val parkingFloorUid: UUID? = null,
)
