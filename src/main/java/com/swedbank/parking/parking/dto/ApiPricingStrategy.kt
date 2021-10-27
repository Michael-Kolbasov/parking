package com.swedbank.parking.parking.dto

import com.swedbank.parking.parking.model.PricingStrategy

enum class ApiPricingStrategy {
    FLOOR_OCCUPANCY,
    ;

    fun toModel() = when (this) {
        FLOOR_OCCUPANCY -> PricingStrategy.FLOOR_OCCUPANCY
    }
}
