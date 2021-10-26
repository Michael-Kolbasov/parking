package com.swedbank.parking.parking.controller

import com.swedbank.parking.AbstractIntegrationTest
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ParkingControllerTest : AbstractIntegrationTest() {
    @Test
    fun getParking_success() {
        mockMvc.perform(get(path))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", `is`("launches")))
    }

    companion object {
        private const val path = "/v1/parking"
    }
}
