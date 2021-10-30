package com.swedbank.parking.parking.validation

import com.swedbank.parking.common.exception.ParkingError
import com.swedbank.parking.common.exception.ParkingException
import com.swedbank.parking.common.exception.ValidationError
import com.swedbank.parking.parking.model.ParkingFloor
import java.math.BigDecimal

class ParkingFloorValidator {
    companion object {
        @JvmStatic
        fun validateHeightChange(parkingFloor: ParkingFloor, height: BigDecimal) {
            val highestOccupyingVehicleHeight = parkingFloor.lots
                .mapNotNull { it.occupiedBy }
                .maxOf { it.height }
            if (height <= highestOccupyingVehicleHeight) {
                val msg = "Height $height is too small for currently occupying vehicle with height $highestOccupyingVehicleHeight"
                throw ParkingException(
                    errorInfo = ParkingError.VALIDATION_EXCEPTION,
                    message = msg,
                    validationErrors = listOf(
                        ValidationError(
                            field = "height",
                            message = msg,
                        )
                    )
                )
            }
        }

        @JvmStatic
        fun validateWeightCapacityChange(parkingFloor: ParkingFloor, weight: BigDecimal) {
            val currentWeightLoad = parkingFloor.lots
                .mapNotNull { it.occupiedBy }
                .sumOf { it.weight }
            if (weight <= currentWeightLoad) {
                val msg = "Weight $weight is too small for current weight load of $currentWeightLoad"
                throw ParkingException(
                    errorInfo = ParkingError.VALIDATION_EXCEPTION,
                    message = msg,
                    validationErrors = listOf(
                        ValidationError(
                            field = "weight",
                            message = msg,
                        )
                    )
                )
            }
        }
    }
}
