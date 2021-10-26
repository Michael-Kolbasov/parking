package com.swedbank.parking.common.controller

import com.swedbank.parking.ParkingApplication
import com.swedbank.parking.common.exception.ParkingError
import com.swedbank.parking.common.exception.ParkingException
import com.swedbank.parking.common.mapper.ValidationErrorMapper
import com.swedbank.parking.common.model.LoggerCompanion
import org.springframework.context.MessageSource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.ZonedDateTime

@RestControllerAdvice
class ControllerExceptionHandler(
    private val messageSource: MessageSource,
    private val validationErrorMapper: ValidationErrorMapper,
) {
    @ExceptionHandler
    fun handle(ex: ParkingException): ResponseEntity<ExceptionResponse> {
        return getResponseEntity(ex)
    }

    @ExceptionHandler
    fun handle(ex: Exception): ResponseEntity<ExceptionResponse> {
        return getResponseEntity(
            ParkingException(
                errorInfo = ParkingError.INTERNAL_ERROR,
                message = ex.message,
                cause = ex,
            )
        )
    }

    private fun getResponseEntity(ex: ParkingException): ResponseEntity<ExceptionResponse> {
        log.error("Exception on http request", ex)
        return ResponseEntity
            .status(ex.errorInfo.getHttpStatus())
            .body(getExceptionResponse(ex))
    }

    private fun getExceptionResponse(ex: ParkingException): ExceptionResponse {
        return ExceptionResponse(
            ex.errorInfo.getCode(),
            getMessage(ex),
            ZonedDateTime.now(ParkingApplication.defaultTimeZone),
            ex.validationErrors.map { validationErrorMapper.getValidationErrorDto(it) },
        )
    }

    private fun getMessage(ex: ParkingException): String? {
        return messageSource.getMessage(
            ex.errorInfo.getCode(),
            null,
            "Something went wrong",
            ParkingApplication.defaultLocale,
        )
    }

    companion object : LoggerCompanion(ControllerExceptionHandler::class)
}
