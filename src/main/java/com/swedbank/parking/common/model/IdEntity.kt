package com.swedbank.parking.common.model

import com.swedbank.parking.common.exception.ParkingError
import com.swedbank.parking.common.exception.ParkingException
import javax.persistence.AttributeConverter
import kotlin.reflect.KClass

interface IdEntity<ID> {
    val id: ID

    abstract class Lookup<ID, IE : IdEntity<ID>>(
        allValuesProvider: () -> Array<IE>
    ) {
        abstract val clazz: KClass<out IdEntity<ID>>
        private val lookup = allValuesProvider().associateBy { it.id }

        fun getById(id: ID?): IE? = lookup[id]

        fun getByIdNN(id: ID): IE = getById(id) ?: throw ParkingException(
            errorInfo = ParkingError.INTERNAL_ERROR,
            message = "Not found ${clazz.simpleName} for id: $id",
        )
    }

    abstract class IdJpaConverter<ID, IE : IdEntity<ID>>(private val lookup: Lookup<ID, IE>) :
        AttributeConverter<IE, ID> {
        override fun convertToDatabaseColumn(entity: IE?): ID? = entity?.id
        override fun convertToEntityAttribute(id: ID?): IE? = lookup.getById(id)
    }
}
