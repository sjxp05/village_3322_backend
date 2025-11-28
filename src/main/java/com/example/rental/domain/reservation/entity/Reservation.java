package com.example.rental.domain.reservation.entity;

import com.example.rental.common.BaseTimeEntity;
import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "reservations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Long initialPaidFee;

    @Column(nullable = false, unique = true)
    private String qrToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Rental rental;

    @Builder
    public Reservation(User user, Item item, Long initialPaidFee, ReservationStatus status) {
        this.user = user;
        this.item = item;
        this.initialPaidFee = initialPaidFee;
        this.qrToken = UUID.randomUUID().toString();
        this.status = status != null ? status : ReservationStatus.PAID;
    }

    public void startUsage() {
        if (this.status != ReservationStatus.PAID) {
            throw new IllegalStateException("Can only start usage from PAID status");
        }
        this.status = ReservationStatus.IN_USE;
    }

    public void markReturned() {
        if (this.status != ReservationStatus.IN_USE) {
            throw new IllegalStateException("Can only return from IN_USE status");
        }
        this.status = ReservationStatus.RETURNED;
    }

    public void cancel() {
        if (this.status != ReservationStatus.PAID) {
            throw new IllegalStateException("Can only cancel from PAID status");
        }
        this.status = ReservationStatus.CANCELED;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }
}
