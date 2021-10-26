package com.swedbank.parking.common.exception

open class NotFoundException(
    message: String? = null,
    validationErrors: Collection<ValidationError> = listOf(),
    cause: Throwable? = null,
) : ParkingException(
    errorInfo = ParkingError.NOT_FOUND,
    validationErrors = validationErrors,
    message = message,
    cause = cause,
)
