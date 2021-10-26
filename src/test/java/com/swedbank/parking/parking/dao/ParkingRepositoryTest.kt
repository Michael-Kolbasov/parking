package com.swedbank.parking.parking.dao

import com.swedbank.parking.AbstractIntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class ParkingRepositoryTest : AbstractIntegrationTest() {
    @Autowired
    private lateinit var parkingRepository: ParkingRepository

    @Test
    fun findByUid_success() {
        val parking = testUtils.createRandomParking()
        assertEquals(parking, parkingRepository.findByUid(parking.uid))
    }
}
