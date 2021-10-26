package com.swedbank.parking.common.model

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

abstract class LoggerCompanion(clazz: KClass<out Any>) {
    val log: Logger = LoggerFactory.getLogger(clazz.java)
}
