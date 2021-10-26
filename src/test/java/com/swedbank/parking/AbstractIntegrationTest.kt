package com.swedbank.parking

import com.swedbank.parking.common.config.IntegrationTestConfig
import com.swedbank.parking.common.util.TestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest(
    classes = [IntegrationTestConfig::class],
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@ActiveProfiles("default", "integration")
@ConfigurationPropertiesScan
abstract class AbstractIntegrationTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var testUtils: TestUtils
}
