package com.swedbank.parking.parking.exception

import com.swedbank.parking.common.config.Prefix
import com.swedbank.parking.common.exception.ParkingErrorInfo
import org.springframework.http.HttpStatus

enum class PricingError(
    private val code: String,
    private val httpStatus: HttpStatus,
) : ParkingErrorInfo {
    STRATEGY_NOT_FOUND("strategy.not.found", HttpStatus.NOT_FOUND),
    ;

    override fun getCode() = "${Prefix.error}.$code"
    override fun getHttpStatus() = httpStatus
}
