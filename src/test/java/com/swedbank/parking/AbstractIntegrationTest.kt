package com.swedbank.parking

import com.swedbank.parking.common.config.IntegrationTestConfig
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [IntegrationTestConfig::class],
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@ConfigurationPropertiesScan
abstract class AbstractIntegrationTest {
}
