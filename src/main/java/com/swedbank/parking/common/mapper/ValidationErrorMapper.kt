package com.swedbank.parking.common.mapper

import com.swedbank.parking.common.controller.ValidationErrorDto
import com.swedbank.parking.common.exception.ValidationError
import org.springframework.stereotype.Component

@Component
class ValidationErrorMapper {
    fun getValidationErrorDto(validationError: ValidationError) = with(validationError) {
        ValidationErrorDto(
            field = field,
            message = message,
        )
    }
}
