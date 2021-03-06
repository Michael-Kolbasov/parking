package com.swedbank.parking.parking.model

import java.time.Instant
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "parking")
class Parking(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    var id: Long? = null,

    @Column(name = "p_uid")
    var uid: UUID = UUID.randomUUID(),

    @Column(name = "p_name")
    var name: String,

    @Column(name = "p_created")
    var created: Instant = Instant.now(),

    @Column(name = "p_updated")
    var updated: Instant = Instant.now(),

    @OneToMany(mappedBy = "parking", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var floors: MutableSet<ParkingFloor> = mutableSetOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Parking) return false
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
        return "Parking{" +
                "id=$id, " +
                "uid=$uid, " +
                "name=$name, " +
                "created=$created, " +
                "updated=$updated" +
                "}"
    }
}
