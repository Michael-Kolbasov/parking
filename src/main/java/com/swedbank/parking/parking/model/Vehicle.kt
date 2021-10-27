package com.swedbank.parking.parking.model

import java.math.BigDecimal
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "vehicle")
class Vehicle(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "v_id")
    var id: Long? = null,

    @Column(name = "v_uid")
    var uid: UUID = UUID.randomUUID(),

    @Column(name = "v_weight")
    var weight: BigDecimal,

    @Column(name = "v_height")
    var height: BigDecimal,
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Vehicle) return false
        return if (id != null && other.id != null) {
            id == other.id
        } else {
            uid == other.uid
        }
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: uid.hashCode()
    }

    override fun toString(): String {
        return "Vehicle{" +
                "id=$id, " +
                "uid=$uid, " +
                "weight=$weight, " +
                "height=$height" +
                "}"
    }
}
