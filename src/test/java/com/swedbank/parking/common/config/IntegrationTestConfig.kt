package com.swedbank.parking.common.config

import com.swedbank.parking.ParkingApplication
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

@SpringBootConfiguration
@ComponentScan(
    basePackageClasses = [ParkingApplication::class],
    excludeFilters = [
        ComponentScan.Filter(
            type = FilterType.ANNOTATION,
            classes = [SpringBootApplication::class],
        ),
    ]
)
@EnableAutoConfiguration
class IntegrationTestConfig {
}
