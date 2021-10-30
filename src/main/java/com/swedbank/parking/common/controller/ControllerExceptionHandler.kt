package com.swedbank.parking.common.controller

import com.swedbank.parking.ParkingApplication
import com.swedbank.parking.common.exception.ParkingError
import com.swedbank.parking.common.exception.ParkingException
import com.swedbank.parking.common.exception.ValidationsExtractor
import com.swedbank.parking.common.mapper.ValidationErrorMapper
import com.swedbank.parking.common.model.LoggerCompanion
import org.springframework.context.MessageSource
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.time.ZonedDateTime
import javax.validation.ConstraintViolationException

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

    @ExceptionHandler
    fun handle(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionResponse> {
        return getResponseEntity(
            ParkingException(
                errorInfo = ParkingError.VALIDATION_EXCEPTION,
                message = ex.message,
                cause = ex,
                validationErrors = ValidationsExtractor.extract(ex),
            )
        )
    }

    @ExceptionHandler
    fun handle(ex: BindException): ResponseEntity<ExceptionResponse> {
        return getResponseEntity(
            ParkingException(
                errorInfo = ParkingError.VALIDATION_EXCEPTION,
                message = ex.message,
                cause = ex,
                validationErrors = ValidationsExtractor.extract(ex),
            )
        )
    }

    @ExceptionHandler
    fun handle(ex: MissingServletRequestParameterException): ResponseEntity<ExceptionResponse> {
        return getResponseEntity(
            ParkingException(
                errorInfo = ParkingError.VALIDATION_EXCEPTION,
                message = ex.message,
                cause = ex,
                validationErrors = ValidationsExtractor.extract(ex),
            )
        )
    }

    @ExceptionHandler
    fun handle(ex: MethodArgumentTypeMismatchException): ResponseEntity<ExceptionResponse> {
        return getResponseEntity(
            ParkingException(
                errorInfo = ParkingError.VALIDATION_EXCEPTION,
                message = ex.message,
                cause = ex,
                validationErrors = ValidationsExtractor.extract(ex),
            )
        )
    }

    @ExceptionHandler
    fun handle(ex: ConstraintViolationException): ResponseEntity<ExceptionResponse> {
        return getResponseEntity(
            ParkingException(
                errorInfo = ParkingError.VALIDATION_EXCEPTION,
                message = ex.message,
                cause = ex,
                validationErrors = ValidationsExtractor.extract(ex),
            )
        )
    }

    @ExceptionHandler
    fun handle(ex: HttpRequestMethodNotSupportedException): ResponseEntity<ExceptionResponse> {
        return getResponseEntity(
            ParkingException(
                errorInfo = ParkingError.METHOD_NOT_SUPPORTED,
                message = ex.message,
                cause = ex,
            )
        )
    }

    @ExceptionHandler
    fun handle(ex: DataIntegrityViolationException): ResponseEntity<ExceptionResponse> {
        return getResponseEntity(
            ParkingException(
                errorInfo = ParkingError.VALIDATION_EXCEPTION,
                message = ex.message,
                cause = ex,
            )
        )
    }

    @ExceptionHandler
    fun handle(ex: HttpMessageNotReadableException): ResponseEntity<ExceptionResponse> {
        return try {
            getResponseEntity(
                ParkingException(
                    errorInfo = ParkingError.VALIDATION_EXCEPTION,
                    message = ex.message,
                    cause = ex,
                    validationErrors = ValidationsExtractor.extract(ex),
                )
            )
        } catch (ex: Exception) {
            getResponseEntity(
                ParkingException(
                    errorInfo = ParkingError.INTERNAL_ERROR,
                    message = ex.message,
                    cause = ex,
                )
            )
        }
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
            ZonedDateTime.now(ParkingApplication.utc),
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
