package com.example.rental.domain.reservation.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.rental.common.BaseTimeEntity;
import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.user.entity.User;

import jakarta.persistence.*;
import lombok.*;

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
    private Long usageDays;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long initialPaidFee;

    @Setter
    @Column
    private Long actualPaidFee;

    @Column(nullable = false, unique = true)
    private String qrToken;

    @Column(length = 500)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime startedAt;

    @Column(nullable = true)
    private LocalDateTime endedAt;

    @Builder
    public Reservation(User user, Item item, Long usageDays, Long initialPaidFee, ReservationStatus status) {
        this.user = user;
        this.item = item;
        this.usageDays = usageDays;
        this.initialPaidFee = initialPaidFee;
        this.qrToken = UUID.randomUUID().toString();
        this.status = status != null ? status : ReservationStatus.PAID;
    }

    public void startUsage() {
        if (this.status != ReservationStatus.PAID) {
            throw new IllegalStateException("Can only start usage from PAID status");
        }
        this.status = ReservationStatus.IN_USE;
        this.startedAt = LocalDateTime.now();
    }

    public void markReturned() {
        if (this.status != ReservationStatus.IN_USE) {
            throw new IllegalStateException("Can only return from IN_USE status");
        }

        this.endedAt = LocalDateTime.now();
        this.status = ReservationStatus.RETURNED;
    }

    public void markLost() {
        this.endedAt = LocalDateTime.now();
        this.status = ReservationStatus.LOST;
    }

    public void markDamaged(Long actualPaidFee) {
        this.endedAt = LocalDateTime.now();
        this.actualPaidFee = actualPaidFee;
        this.status = ReservationStatus.DAMAGED;
    }

    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(this.startedAt.plusDays(this.usageDays));
    }

    public void cancel() {
        if (this.status != ReservationStatus.PAID) {
            throw new IllegalStateException("Can only cancel from PAID status");
        }
        this.status = ReservationStatus.CANCELED;
    }

    public void extendUsageDays(Long additionalDays) {
        if (this.status != ReservationStatus.IN_USE) {
            throw new IllegalStateException("Can only extend usage days when IN_USE");
        }
        this.usageDays += additionalDays;
    }

}
