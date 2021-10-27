package com.swedbank.parking.parking.service

import com.swedbank.parking.parking.dto.ParkingTicketCreateRequest
import com.swedbank.parking.parking.dto.ParkingTicketDto
import com.swedbank.parking.parking.mapper.ParkingTicketMapper
import com.swedbank.parking.parking.mapper.VehicleMapper
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ParkingTicketRestService(
    private val parkingService: ParkingService,
    private val parkingTicketAssignService: ParkingTicketAssignService,
    private val vehicleMapper: VehicleMapper,
    private val parkingTicketMapper: ParkingTicketMapper,
) {
    fun create(
        parkingUid: UUID,
        request: ParkingTicketCreateRequest,
    ): ParkingTicketDto {
        val parking = parkingService.getByUidFetchingFloorsAndLotsLockedNN(parkingUid)
        val assignedTicket = parkingTicketAssignService.assign(
            parking = parking,
            vehicle = vehicleMapper.getVehicle(request.vehicle!!),
        )
        return parkingTicketMapper.getTicketDto(assignedTicket)
    }
}
