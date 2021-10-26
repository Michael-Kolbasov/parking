package com.swedbank.parking.parking.controller

import com.swedbank.parking.AbstractIntegrationTest
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.UUID

class ParkingControllerTest : AbstractIntegrationTest() {
    @Test
    fun getByUid_success() {
        val parking = testUtils.createRandomParking()
        mockMvc.perform(get("$path/${parking.uid}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.uid", `is`(parking.uid.toString())))
            .andExpect(jsonPath("$.name", `is`(parking.name)))
    }

    @Test
    fun getByUid_notFound() {
        mockMvc.perform(get("$path/${UUID.randomUUID()}"))
            .andExpect(status().isNotFound)
    }

    companion object {
        private const val path = "/v1/parking"
    }
}
