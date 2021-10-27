package com.swedbank.parking.parking.exception

import com.swedbank.parking.common.config.Prefix
import com.swedbank.parking.common.exception.ParkingErrorInfo
import org.springframework.http.HttpStatus

enum class ParkingLotError(
    private val code: String,
    private val httpStatus: HttpStatus,
) : ParkingErrorInfo {
    NOT_FOUND_AVAILABLE_LOT("lot.available.not.found", HttpStatus.NOT_FOUND),
    ;

    override fun getCode() = "${Prefix.error}.$code"
    override fun getHttpStatus() = httpStatus
}
