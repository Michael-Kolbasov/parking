package com.swedbank.parking.parking.service

import com.swedbank.parking.common.exception.ParkingException
import com.swedbank.parking.parking.exception.ParkingLotError
import com.swedbank.parking.parking.mapper.ParkingTicketMapper
import com.swedbank.parking.parking.model.Parking
import com.swedbank.parking.parking.model.ParkingLot
import com.swedbank.parking.parking.model.ParkingTicket
import com.swedbank.parking.parking.model.Vehicle
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class ParkingTicketAssignService(
    private val parkingTicketService: ParkingTicketService,
    private val parkingTicketMapper: ParkingTicketMapper,
    private val parkingLotService: ParkingLotService,
    private val vehicleService: VehicleService,
) {
    @Transactional
    fun assign(
        parking: Parking,
        vehicle: Vehicle,
    ): ParkingTicket {
        val availableLots = getAvailableLots(parking, vehicle)
        if (availableLots.isEmpty()) {
            throw ParkingException(
                errorInfo = ParkingLotError.NOT_FOUND_AVAILABLE_LOT,
                message = "Parking ${parking.uid} has no available parking lots.",
            )
        }
        vehicleService.saveOrUpdate(vehicle)
        // todo add strategy with specific rules to pick a lot, picking any for now
        val pickedLot = availableLots.first()
            .apply {
                occupiedBy = vehicle
                updated = Instant.now()
            }.also {
                parkingLotService.saveOrUpdate(it)
            }
        return parkingTicketService.saveOrUpdate(
            parkingTicketMapper.getTicket(
                lot = pickedLot,
            )
        )
    }

    private fun getAvailableLots(parking: Parking, vehicle: Vehicle): Collection<ParkingLot> {
        val floorIdToCurrentlyHeldWeight = parking.floors
            .associate { floor ->
                floor.id to floor.lots
                    .mapNotNull { it.occupiedBy }
                    .sumOf { it.weight }
            }
        return parking.floors
            .asSequence()
            .filter { it.height > vehicle.height } // not >=, we want to have reserved space, right?
            .filter { it.weightCapacity >= floorIdToCurrentlyHeldWeight[it.id]!! + vehicle.weight }
            .flatMap { it.lots }
            .filter { it.occupiedBy == null }
            .toList()
    }
}
