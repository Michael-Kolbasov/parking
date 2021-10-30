package com.swedbank.parking.parking.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.util.UUID
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@JsonIgnoreProperties(ignoreUnknown = true)
data class ParkingFloorUpsertRequest(
    @field:NotNull
    val parkingUid: UUID,
    @field:[NotBlank Size(min = 1)]
    val name: String? = null,
    @field:[NotNull Min(1)]
    val height: BigDecimal? = null,
    @field:[NotNull Min(1)]
    val weightCapacity: BigDecimal? = null,
)
