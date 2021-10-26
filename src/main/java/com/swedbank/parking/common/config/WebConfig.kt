package com.swedbank.parking.common.config

import com.swedbank.parking.ParkingApplication
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.support.ResourceBundleMessageSource

@Configuration
class WebConfig {
    @Bean
    @Primary
    fun messageSource(): MessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setDefaultEncoding(ParkingApplication.defaultEncoding.name())
        messageSource.setBasename(ParkingApplication.messageSourceName)
        messageSource.setDefaultLocale(ParkingApplication.defaultLocale)
        return messageSource
    }
}
