package com.swedbank.parking.common.util

import com.swedbank.parking.parking.config.ParkingProperties
import com.swedbank.parking.parking.dao.ParkingFloorRepository
import com.swedbank.parking.parking.dao.ParkingLotRepository
import com.swedbank.parking.parking.dao.ParkingRepository
import com.swedbank.parking.parking.model.Parking
import com.swedbank.parking.parking.model.ParkingFloor
import com.swedbank.parking.parking.model.ParkingLot
import org.apache.commons.lang3.RandomUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.UUID

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
            ),
        ),
        weightCapacity = BigDecimal.valueOf(
            RandomUtils.nextDouble(
                parkingProperties.floor.weight.min!!.toDouble(),
                parkingProperties.floor.weight.max!!.toDouble(),
            ),
        ),
        parking = getRandomParking(parkingCustomizer)
    ).also(customizer)

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
}
