package com.swedbank.parking.parking.controller

import com.swedbank.parking.AbstractIntegrationTest
import com.swedbank.parking.parking.config.ParkingProperties
import com.swedbank.parking.parking.dto.ApiPricingStrategy
import com.swedbank.parking.parking.dto.ParkingTicketAssignRequest
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.everyItem
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ParkingTicketControllerTest : AbstractIntegrationTest() {
    @Autowired
    private lateinit var parkingProperties: ParkingProperties

    @Test
    fun assign_success() {
        val lot = testUtils.createRandomParkingLot()
        val request = ParkingTicketAssignRequest(
            vehicle = ParkingTicketAssignRequest.Vehicle(
                weight = testUtils.getRandomBigDecimal(
                    min = 1.0,
                    max = lot.floor.weightCapacity.toDouble(),
                ),
                height = testUtils.getRandomBigDecimal(
                    min = 1.0,
                    max = lot.floor.height.toDouble(),
                ),
            ),
        )
        mockMvc.perform(
            post(path, lot.floor.parking.uid)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
                .param("strategy", ApiPricingStrategy.DEFAULT.name)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.pricePerMinute", `is`(parkingProperties.ticket.price.base!!.toDouble())))
            .andExpect(jsonPath("$.pricingStrategy", `is`(ApiPricingStrategy.DEFAULT.name)))
            .andExpect(jsonPath("$.lot.uid", `is`(lot.uid.toString())))
    }

    @Test
    fun assign_validation_success() {
        val lot = testUtils.createRandomParkingLot()
        val request = ParkingTicketAssignRequest(
            vehicle = ParkingTicketAssignRequest.Vehicle(),
        )
        mockMvc.perform(
            post(path, lot.floor.parking.uid)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest)
            .andExpect(jsonPath(
                "$.validations.*.message",
                everyItem(`is`("must not be null"))
            ))
    }

    companion object {
        private const val path = "/v1/parking/ticket/{parkingUid}"
    }
}
