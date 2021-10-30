package com.swedbank.parking.parking.mapper

import com.swedbank.parking.parking.dto.ParkingTicketAssignRequest
import com.swedbank.parking.parking.model.Vehicle
import org.springframework.stereotype.Component

@Component
class VehicleMapper {
    fun getVehicle(vehicle: ParkingTicketAssignRequest.Vehicle): Vehicle = with (vehicle) {
        Vehicle(
            weight = weight!!,
            height = height!!,
        )
    }
}
