package com.swedbank.parking.parking.config

import com.sun.istack.NotNull
import com.swedbank.parking.common.config.Prefix
import com.swedbank.parking.parking.model.PricingStrategy
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import java.math.BigDecimal
import javax.validation.constraints.Min

@ConfigurationProperties(prefix = Prefix.parking)
@ConstructorBinding
@Validated
data class ParkingProperties(
    @Validated
    val floor: Floor,
    @Validated
    val ticket: Ticket,
) {
    data class Floor(
        @Validated
        val height: Height,
        @Validated
        val weight: Weight,
    ) {
        data class Height(
            @field:[NotNull Min(0)]
            val min: BigDecimal? = null,
            @field:[NotNull Min(0)]
            val max: BigDecimal? = null,
        )

        data class Weight(
            @field:[NotNull Min(0)]
            val min: BigDecimal? = null,
            @field:[NotNull Min(0)]
            val max: BigDecimal? = null,
        )
    }

    data class Ticket(
        @Validated
        val price: Price,
    ) {
        data class Price(
            @field:[NotNull Min(0)]
            val avg: BigDecimal? = null,
            @Validated
            val strategy: Strategy,
        ) {
            data class Strategy(
                @field:NotNull
                val default: PricingStrategy? = null,
            )
        }
    }
}
