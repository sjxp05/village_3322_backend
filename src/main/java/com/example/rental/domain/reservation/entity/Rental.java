package com.example.rental.domain.reservation.entity;

import java.time.LocalDateTime;

import com.example.rental.common.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rentals")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rental extends BaseTimeEntity {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @Column(nullable = false)
    private LocalDateTime timeLimit;

    private Long actualPaidFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalStatus status;

    @Builder
    public Rental(Reservation reservation, LocalDateTime timeLimit) {
        this.reservation = reservation;
        this.startedAt = LocalDateTime.now();
        this.timeLimit = timeLimit;
        this.status = RentalStatus.IN_USE;
    }

    public void completeReturn(Long actualPaidFee) {
        this.endedAt = LocalDateTime.now();
        this.actualPaidFee = actualPaidFee;
        this.status = RentalStatus.RETURNED;
    }

    public void markLost() {
        this.endedAt = LocalDateTime.now();
        this.status = RentalStatus.LOST;
    }

    public void markDamaged(Long actualPaidFee) {
        this.endedAt = LocalDateTime.now();
        this.actualPaidFee = actualPaidFee;
        this.status = RentalStatus.DAMAGED;
    }

    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(this.timeLimit) && this.status == RentalStatus.IN_USE;
    }
}
