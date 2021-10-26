package com.swedbank.parking.parking.dao

import com.swedbank.parking.AbstractIntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class ParkingFloorRepositoryTest : AbstractIntegrationTest() {
    @Autowired
    private lateinit var parkingFloorRepository: ParkingFloorRepository

    @Test
    fun findByUid_success() {
        val floor = testUtils.createRandomParkingFloor()
        assertEquals(floor, parkingFloorRepository.findByUid(floor.uid))
    }
}
