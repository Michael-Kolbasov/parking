package com.swedbank.parking.parking.model

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "parking_floor")
class ParkingFloor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pf_id")
    var id: Long? = null,

    @Column(name = "pf_uid")
    var uid: UUID = UUID.randomUUID(),

    @Column(name = "pf_name")
    var name: String,

    @Column(name = "pf_height")
    var height: BigDecimal,

    @Column(name = "pf_weight_capacity")
    var weightCapacity: BigDecimal,

    @Column(name = "pf_created")
    var created: Instant = Instant.now(),

    @Column(name = "pf_updated")
    var updated: Instant = Instant.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    var parking: Parking,

    @OneToMany(mappedBy = "floor", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var lots: MutableSet<ParkingLot> = mutableSetOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ParkingFloor) return false
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
        return "ParkingFloor{" +
                "id=$id, " +
                "uid=$uid, " +
                "name=$name, " +
                "height=$height, " +
                "weightCapacity=$weightCapacity, " +
                "created=$created, " +
                "updated=$updated, " +
                "parkingId=${parking.id}" +
                "}"
    }
}
