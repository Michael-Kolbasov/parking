package com.swedbank.parking.common.util

import java.math.BigDecimal

operator fun BigDecimal.plus(d: Double): BigDecimal {
    return this + BigDecimal.valueOf(d)
}

operator fun BigDecimal.minus(d: Double): BigDecimal {
    return this - BigDecimal.valueOf(d)
}
