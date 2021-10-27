package com.swedbank.parking.parking.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.sun.istack.NotNull
import java.math.BigDecimal
import javax.validation.Valid
import javax.validation.constraints.Min

@JsonIgnoreProperties(ignoreUnknown = true)
data class ParkingTicketAssignRequest(
    @field:[NotNull Valid]
    val vehicle: Vehicle? = null,
) {
    data class Vehicle(
        @field:[NotNull Min(0)]
        val weight: BigDecimal? = null,
        @field:[NotNull Min(0)]
        val height: BigDecimal? = null,
    )
}
