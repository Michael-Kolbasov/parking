package com.swedbank.parking.common.util

import java.util.UUID
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class UuidConverter : AttributeConverter<UUID, String> {
    override fun convertToDatabaseColumn(attribute: UUID): String {
        return attribute.toString()
    }

    override fun convertToEntityAttribute(dbData: String?): UUID? {
        return if (dbData == null) {
            null
        } else UUID.fromString(dbData)
    }
}
