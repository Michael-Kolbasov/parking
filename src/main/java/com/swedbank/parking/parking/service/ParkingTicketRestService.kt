package com.swedbank.parking.parking.service

import com.swedbank.parking.common.exception.ParkingException
import com.swedbank.parking.common.exception.ValidationError
import com.swedbank.parking.parking.dto.ApiPricingStrategy
import com.swedbank.parking.parking.dto.ParkingTicketCreateRequest
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
        request: ParkingTicketCreateRequest,
        strategy: ApiPricingStrategy,
    ): ParkingTicketDto {
        val parking = parkingService.getByUidFetchingFloorsAndLotsLockedNN(parkingUid)
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
                        message = "not found",
                    )
                ),
                message = "Pricing strategy $strategy not found.",
            )
        // todo push event with price
        return parkingTicketMapper.getTicketDto(
            ticket = assignedTicket,
            pricePerMinute = price,
            pricingStrategy = strategy.toModel(),
        )
    }
}
