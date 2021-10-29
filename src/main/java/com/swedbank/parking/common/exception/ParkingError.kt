package com.swedbank.parking.common.exception

import com.swedbank.parking.common.config.Prefix
import org.springframework.http.HttpStatus

enum class ParkingError(
    private val code: String,
    private val httpStatus: HttpStatus,
) : ParkingErrorInfo {
    INTERNAL_ERROR("internal", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND("not.found", HttpStatus.NOT_FOUND),
    VALIDATION_EXCEPTION("validation", HttpStatus.BAD_REQUEST),
    METHOD_NOT_SUPPORTED("method.not.supported", HttpStatus.METHOD_NOT_ALLOWED)
    ;

    override fun getCode() = "${Prefix.error}.$code"
    override fun getHttpStatus() = httpStatus
}
