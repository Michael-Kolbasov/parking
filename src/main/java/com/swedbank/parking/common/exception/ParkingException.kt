package com.swedbank.parking.common.exception

import org.springframework.http.HttpStatus

open class ParkingException(
    open val errorInfo: ParkingErrorInfo,
    val validationErrors: Collection<ValidationError> = listOf(),
    message: String? = "",
    cause: Throwable? = null,
): RuntimeException(message, cause)

interface ParkingErrorInfo {
    fun getCode(): String
    fun getHttpStatus(): HttpStatus
    fun getMessage(): String = ""
}

data class ValidationError(
    val field: String,
    val message: String,
)
