package com.swedbank.parking.parking.config

import com.sun.istack.NotNull
import com.swedbank.parking.parking.config.ParkingProperties.Companion.parkingPrefix
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import java.math.BigDecimal
import javax.validation.constraints.Min

@ConfigurationProperties(prefix = parkingPrefix)
@ConstructorBinding
@Validated
data class ParkingProperties(
    @Validated
    val floor: Floor,
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

    companion object {
        const val parkingPrefix = "parking"
    }
}
