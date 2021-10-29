package com.swedbank.parking.parking.service

import com.swedbank.parking.common.exception.ParkingException
import com.swedbank.parking.common.exception.ValidationError
import com.swedbank.parking.parking.dto.ApiPricingStrategy
import com.swedbank.parking.parking.dto.ParkingTicketAssignRequest
import com.swedbank.parking.parking.dto.ParkingTicketDto
import com.swedbank.parking.parking.exception.PricingError
import com.swedbank.parking.parking.mapper.ParkingTicketMapper
import com.swedbank.parking.parking.mapper.VehicleMapper
import com.swedbank.parking.parking.model.PricingStrategy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ParkingTicketRestService(
    private val parkingService: ParkingService,
    private val parkingTicketAssignService: ParkingTicketAssignService,
    private val vehicleMapper: VehicleMapper,
    private val parkingTicketMapper: ParkingTicketMapper,
    private val pricingServices: Collection<PricingService>,
) {
    private val pricingServiceByStrategy: Map<PricingStrategy, PricingService> by lazy {
        pricingServices.associateBy { it.pricingStrategy }
    }

    @Transactional
    fun assign(
        parkingUid: UUID,
        request: ParkingTicketAssignRequest,
        strategy: ApiPricingStrategy,
    ): ParkingTicketDto {
        // intentional call to lock the parking row so that concurrent calls would not end up interfering each other
        parkingService.getByUidLockedNN(parkingUid)
        val parking = parkingService.getByUidFetchingFloorsAndLotsNN(parkingUid)
        val assignedTicket = parkingTicketAssignService.assign(
            parking = parking,
            vehicle = vehicleMapper.getVehicle(request.vehicle!!),
        )
        val price = pricingServiceByStrategy[strategy.toModel()]
            ?.calculatePrice(assignedTicket)
            ?: throw ParkingException(
                errorInfo = PricingError.STRATEGY_NOT_FOUND,
                validationErrors = listOf(
                    ValidationError(
                        field = "strategy",
                        message = "service not found",
                    )
                ),
                message = "Service for pricing strategy $strategy not found.",
            )
        // todo push event with price
        return parkingTicketMapper.getTicketDto(
            ticket = assignedTicket,
            pricePerMinute = price,
            pricingStrategy = strategy.toModel(),
        )
    }
}
