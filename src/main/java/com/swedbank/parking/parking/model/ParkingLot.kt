package com.swedbank.parking.parking.model

import java.time.Instant
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "parking_lot")
class ParkingLot(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pl_id")
    var id: Long? = null,

    @Column(name = "pl_uid")
    var uid: UUID = UUID.randomUUID(),

    @Column(name = "pl_name")
    var name: String,

    @Column(name = "pl_created")
    var created: Instant = Instant.now(),

    @Column(name = "pl_updated")
    var updated: Instant = Instant.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pf_id")
    var floor: ParkingFloor,

    @OneToOne
    @JoinColumn(name = "pl_occupied_by_v_id")
    var occupiedBy: Vehicle? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ParkingLot) return false
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
        return "ParkingLot{" +
                "id=$id, " +
                "uid=$uid, " +
                "name=$name, " +
                "created=$created, " +
                "updated=$updated, " +
                "floorId=${floor.id}, " +
                "occupiedById=${occupiedBy?.id} " +
                "}"
    }
}
