package com.swedbank.parking.common.util

import com.swedbank.parking.parking.config.ParkingProperties
import com.swedbank.parking.parking.dao.ParkingFloorRepository
import com.swedbank.parking.parking.dao.ParkingLotRepository
import com.swedbank.parking.parking.dao.ParkingRepository
import com.swedbank.parking.parking.dao.ParkingTicketRepository
import com.swedbank.parking.parking.dao.VehicleRepository
import com.swedbank.parking.parking.model.Parking
import com.swedbank.parking.parking.model.ParkingFloor
import com.swedbank.parking.parking.model.ParkingLot
import com.swedbank.parking.parking.model.ParkingTicket
import com.swedbank.parking.parking.model.Vehicle
import org.apache.commons.lang3.RandomUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.UUID
import kotlin.math.roundToLong

@TestComponent
class TestUtils {
    @Autowired
    lateinit var parkingProperties: ParkingProperties
    @Autowired
    private lateinit var parkingRepository: ParkingRepository
    @Autowired
    private lateinit var parkingFloorRepository: ParkingFloorRepository
    @Autowired
    private lateinit var parkingLotRepository: ParkingLotRepository
    @Autowired
    private lateinit var parkingTicketRepository: ParkingTicketRepository
    @Autowired
    private lateinit var vehicleRepository: VehicleRepository

    @Transactional
    fun createRandomParking(
        customizer: (Parking) -> Unit = {},
    ): Parking = parkingRepository.saveAndFlush(
        getRandomParking(customizer)
    )

    @Transactional
    fun createRandomParkingFloor(
        customizer: (ParkingFloor) -> Unit = {},
        parkingCustomizer: (Parking) -> Unit = {},
    ): ParkingFloor {
        val floor = getRandomParkingFloor(customizer, parkingCustomizer)
        parkingRepository.saveAndFlush(floor.parking)
        return parkingFloorRepository.saveAndFlush(floor)
    }

    @Transactional
    fun createRandomParkingLot(
        customizer: (ParkingLot) -> Unit = {},
        parkingFloorCustomizer: (ParkingFloor) -> Unit = {},
        parkingCustomizer: (Parking) -> Unit = {},
    ): ParkingLot {
        val lot = getRandomParkingLot(customizer, parkingFloorCustomizer, parkingCustomizer)
        parkingRepository.saveAndFlush(lot.floor.parking)
        parkingFloorRepository.saveAndFlush(lot.floor)
        return parkingLotRepository.saveAndFlush(lot)
    }

    @Transactional
    fun createRandomParkingTicket(
        customizer: (ParkingTicket) -> Unit = {},
    ): ParkingTicket {
        val ticket = getRandomParkingTicket(customizer)
        parkingRepository.saveAndFlush(ticket.lot.floor.parking)
        parkingFloorRepository.saveAndFlush(ticket.lot.floor)
        parkingLotRepository.saveAndFlush(ticket.lot)
        return parkingTicketRepository.saveAndFlush(ticket)
    }

    @Transactional
    fun createRandomVehicle(
        customizer: (Vehicle) -> Unit = {},
    ): Vehicle = vehicleRepository.saveAndFlush(
        getRandomVehicle(customizer)
    )

    fun getRandomParking(
        customizer: (Parking) -> Unit = {},
    ) = Parking(
        name = UUID.randomUUID().toString(),
    ).also(customizer)

    fun getRandomParkingFloor(
        customizer: (ParkingFloor) -> Unit = {},
        parkingCustomizer: (Parking) -> Unit = {},
    ) = ParkingFloor(
        name = UUID.randomUUID().toString(),
        height = with(parkingProperties.floor.weight) {
            getRandomBigDecimal(
                min = min!!.toDouble(),
                max = max!!.toDouble(),
            )
        },
        weightCapacity = with(parkingProperties.floor.weight) {
            getRandomBigDecimal(
                min = min!!.toDouble(),
                max = max!!.toDouble(),
            )
        },
        parking = getRandomParking(parkingCustomizer)
    ).also(customizer)

    fun getRandomParkingLot(
        customizer: (ParkingLot) -> Unit = {},
        parkingFloorCustomizer: (ParkingFloor) -> Unit = {},
        parkingCustomizer: (Parking) -> Unit = {},
    ) = ParkingLot(
        name = UUID.randomUUID().toString(),
        floor = getRandomParkingFloor(
            customizer = parkingFloorCustomizer,
            parkingCustomizer = parkingCustomizer,
        )
    ).also(customizer)

    fun getRandomParkingTicket(
        customizer: (ParkingTicket) -> Unit = {},
    ) = ParkingTicket(
        lot = getRandomParkingLot(),
    ).also(customizer)

    fun getRandomVehicle(
        customizer: (Vehicle) -> Unit = {},
    ) = Vehicle(
        weight = getRandomBigDecimal(
            min = 50.0,
            max = 100.0
        ),
        height = getRandomBigDecimal(
            min = 50.0,
            max = 100.0
        ),
    )

    fun getRandomBigDecimal(
        min: Double = Double.MIN_VALUE,
        max: Double = Double.MAX_VALUE,
    ): BigDecimal = BigDecimal.valueOf(
        RandomUtils.nextDouble(min, max).roundToLong(),
    )
}
