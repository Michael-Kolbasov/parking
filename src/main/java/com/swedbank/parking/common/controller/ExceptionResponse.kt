package com.swedbank.parking.common.controller

import java.time.ZonedDateTime

data class ExceptionResponse(
    var code: String,
    var message: String? = null,
    var dateTime: ZonedDateTime? = null,
    var validations: List<ValidationErrorDto>? = null,
)

data class ValidationErrorDto(
    val field: String? = null,
    val message: String? = null,
)
