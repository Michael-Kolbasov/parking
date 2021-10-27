package com.swedbank.parking.common.util

import com.swedbank.parking.parking.config.ParkingProperties
import com.swedbank.parking.parking.dao.ParkingFloorRepository
import com.swedbank.parking.parking.dao.ParkingLotRepository
import com.swedbank.parking.parking.dao.ParkingRepository
import com.swedbank.parking.parking.dao.ParkingTicketRepository
import com.swedbank.parking.parking.model.Parking
import com.swedbank.parking.parking.model.ParkingFloor
import com.swedbank.parking.parking.model.ParkingLot
import com.swedbank.parking.parking.model.ParkingTicket
import org.apache.commons.lang3.RandomUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.Duration
import java.time.Instant
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

    @Transactional
    fun createRandomParking(): Parking = createRandomParking(
        customizer = {},
    )

    @Transactional
    fun createRandomParking(
        customizer: (Parking) -> Unit,
    ): Parking = parkingRepository.saveAndFlush(
        getRandomParking(customizer)
    )

    @Transactional
    fun createRandomParkingFloor(): ParkingFloor = createRandomParkingFloor(
        customizer = {},
        parkingCustomizer = {},
    )

    @Transactional
    fun createRandomParkingFloor(
        customizer: (ParkingFloor) -> Unit,
        parkingCustomizer: (Parking) -> Unit,
    ): ParkingFloor {
        val floor = getRandomParkingFloor(customizer, parkingCustomizer)
        parkingRepository.saveAndFlush(floor.parking)
        return parkingFloorRepository.saveAndFlush(floor)
    }

    @Transactional
    fun createRandomParkingLot(): ParkingLot = createRandomParkingLot(
        customizer = {},
        parkingFloorCustomizer = {},
        parkingCustomizer = {},
    )

    @Transactional
    fun createRandomParkingLot(
        customizer: (ParkingLot) -> Unit,
        parkingFloorCustomizer: (ParkingFloor) -> Unit,
        parkingCustomizer: (Parking) -> Unit,
    ): ParkingLot {
        val lot = getRandomParkingLot(customizer, parkingFloorCustomizer, parkingCustomizer)
        parkingRepository.saveAndFlush(lot.floor.parking)
        parkingFloorRepository.saveAndFlush(lot.floor)
        return parkingLotRepository.saveAndFlush(lot)
    }

    @Transactional
    fun createRandomParkingTicket(): ParkingTicket = createRandomParkingTicket(
        customizer = {},
    )

    @Transactional
    fun createRandomParkingTicket(
        customizer: (ParkingTicket) -> Unit,
    ): ParkingTicket {
        val ticket = getRandomParkingTicket(customizer)
        parkingRepository.saveAndFlush(ticket.lot.floor.parking)
        parkingFloorRepository.saveAndFlush(ticket.lot.floor)
        parkingLotRepository.saveAndFlush(ticket.lot)
        return parkingTicketRepository.saveAndFlush(ticket)
    }

    fun getRandomParking(
        customizer: (Parking) -> Unit,
    ) = Parking(
        name = UUID.randomUUID().toString(),
    ).also(customizer)

    fun getRandomParkingFloor(
        customizer: (ParkingFloor) -> Unit,
        parkingCustomizer: (Parking) -> Unit,
    ) = ParkingFloor(
        name = UUID.randomUUID().toString(),
        height = BigDecimal.valueOf(
            RandomUtils.nextDouble(
                parkingProperties.floor.height.min!!.toDouble(),
                parkingProperties.floor.height.max!!.toDouble(),
            ).roundToLong(),
        ),
        weightCapacity = BigDecimal.valueOf(
            RandomUtils.nextDouble(
                parkingProperties.floor.weight.min!!.toDouble(),
                parkingProperties.floor.weight.max!!.toDouble(),
            ).roundToLong(),
        ),
        parking = getRandomParking(parkingCustomizer)
    ).also(customizer)

    fun getRandomParkingLot() = getRandomParkingLot(
        customizer = {},
        parkingFloorCustomizer = {},
        parkingCustomizer = {},
    )

    fun getRandomParkingLot(
        customizer: (ParkingLot) -> Unit,
        parkingFloorCustomizer: (ParkingFloor) -> Unit,
        parkingCustomizer: (Parking) -> Unit,
    ) = ParkingLot(
        name = UUID.randomUUID().toString(),
        floor = getRandomParkingFloor(
            customizer = parkingFloorCustomizer,
            parkingCustomizer = parkingCustomizer,
        )
    ).also(customizer)

    fun getRandomParkingTicket(
        customizer: (ParkingTicket) -> Unit,
    ): ParkingTicket {
        val now = Instant.now()
        return ParkingTicket(
            price = BigDecimal.valueOf(
                RandomUtils.nextDouble(
                    (parkingProperties.ticket.price.avg!! - 100.0).toDouble()
                        .coerceAtLeast(0.0),
                    (parkingProperties.ticket.price.avg!! + 100.0).toDouble(),
                ).roundToLong(),
            ),
            validFrom = now,
            validTill = now.plus(Duration.ofMinutes(5)),
            lot = getRandomParkingLot(),
        )
    }
}
