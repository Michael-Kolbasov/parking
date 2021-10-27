package com.swedbank.parking.parking.model

import com.swedbank.parking.parking.dto.ApiPricingStrategy

enum class PricingStrategy {
    DEFAULT,
    FLOOR_OCCUPANCY,
    ;

    fun toApi() = when (this) {
        DEFAULT -> ApiPricingStrategy.DEFAULT
        FLOOR_OCCUPANCY -> ApiPricingStrategy.FLOOR_OCCUPANCY
    }
}
