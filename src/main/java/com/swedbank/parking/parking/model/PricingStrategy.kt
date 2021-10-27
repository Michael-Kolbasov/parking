package com.swedbank.parking.parking.model

import com.swedbank.parking.parking.dto.ApiPricingStrategy

enum class PricingStrategy {
    FLOOR_OCCUPANCY,
    ;

    fun toApi() = when (this) {
        FLOOR_OCCUPANCY -> ApiPricingStrategy.FLOOR_OCCUPANCY
    }
}
