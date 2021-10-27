package com.swedbank.parking.parking.model

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "parking_ticket")
class ParkingTicket(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pt_id")
    var id: Long? = null,

    @Column(name = "pt_uid")
    var uid: UUID = UUID.randomUUID(),

    @Column(name = "pt_price")
    var price: BigDecimal,

    @Column(name = "pt_created")
    var created: Instant = Instant.now(),

    @Column(name = "pt_paid")
    var paid: Boolean = false,

    @Column(name = "pt_paid_at")
    var paidAt: Instant? = null,

    @Column(name = "pt_valid_from")
    var validFrom: Instant,

    @Column(name = "pt_valid_till")
    var validTill: Instant,

    @ManyToOne
    @JoinColumn(name = "pl_id")
    var lot: ParkingLot,
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ParkingTicket) return false
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
        return "ParkingTicket{" +
                "id=$id, " +
                "uid=$uid, " +
                "price=$price, " +
                "created=$created, " +
                "paid=$paid, " +
                "paidAt=$paidAt, " +
                "validFrom=$validFrom, " +
                "validTill=$validTill, " +
                "lotId=${lot.id}" +
                "}"
    }
}
