package com.swedbank.parking.parking.dto

import com.swedbank.parking.parking.model.PricingStrategy

enum class ApiPricingStrategy {
    DEFAULT,
    FLOOR_OCCUPANCY,
    ;

    fun toModel() = when (this) {
        DEFAULT -> PricingStrategy.DEFAULT
        FLOOR_OCCUPANCY -> PricingStrategy.FLOOR_OCCUPANCY
    }
}
