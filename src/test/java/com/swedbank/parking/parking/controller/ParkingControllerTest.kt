package com.swedbank.parking.parking.controller

import com.swedbank.parking.AbstractIntegrationTest
import com.swedbank.parking.parking.dto.ParkingUpsertRequest
import com.swedbank.parking.parking.service.ParkingService
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasItems
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.UUID

class ParkingControllerTest : AbstractIntegrationTest() {
    @Autowired
    private lateinit var parkingService: ParkingService

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

    @Test
    fun create_success() {
        val request = ParkingUpsertRequest(name = UUID.randomUUID().toString())
        mockMvc.perform(post(path)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.name", `is`(request.name)))
    }

    @Test
    fun update_success() {
        val parking = testUtils.createRandomParking()
        val request = ParkingUpsertRequest(name = UUID.randomUUID().toString())
        mockMvc.perform(put("$path/{parkingUid}", parking.uid)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.name", `is`(request.name)))
        assertEquals(request.name, parkingService.getByUidNN(parking.uid).name)
    }

    @Test
    fun getAll_success() {
        val parking1 = testUtils.createRandomParking()
        val parking2 = testUtils.createRandomParking()
        mockMvc.perform(get(path))
            .andExpect(status().isOk)
            .andExpect(jsonPath(
                "$.*.uid",
                hasItems(parking1.uid.toString(), parking2.uid.toString())
            ))
    }

    companion object {
        private const val path = "/v1/parking"
    }
}
