package com.swedbank.parking.common.dto

data class ValidationErrorDto(
    val field: String? = null,
    val message: String? = null,
)
