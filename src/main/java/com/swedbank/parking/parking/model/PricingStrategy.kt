package com.swedbank.parking.parking.model

import com.swedbank.parking.common.model.IdEntity
import com.swedbank.parking.parking.dto.ApiPricingStrategy
import javax.persistence.Converter

enum class PricingStrategy(
    override val id: Long,
    val displayName: String
) : IdEntity<Long> {
    DEFAULT(id = 1, displayName = "default"),
    FLOOR_OCCUPANCY(id = 2, displayName = "floor_occupancy"),
    ;

    fun toApi() = when (this) {
        DEFAULT -> ApiPricingStrategy.DEFAULT
        FLOOR_OCCUPANCY -> ApiPricingStrategy.FLOOR_OCCUPANCY
    }

    @Converter(autoApply = true)
    class JpaConverter : IdEntity.IdJpaConverter<Long, PricingStrategy>(Companion)

    companion object : IdEntity.Lookup<Long, PricingStrategy>({ values() }) {
        override val clazz = PricingStrategy::class
    }
}
