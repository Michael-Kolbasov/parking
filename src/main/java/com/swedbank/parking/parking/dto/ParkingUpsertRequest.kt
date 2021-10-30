package com.swedbank.parking.parking.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@JsonIgnoreProperties(ignoreUnknown = true)
data class ParkingUpsertRequest(
    @field:[NotBlank Size(min = 5)]
    val name: String? = null,
)
