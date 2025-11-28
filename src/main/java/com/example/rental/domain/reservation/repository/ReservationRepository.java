package com.example.rental.domain.reservation.repository;

import com.example.rental.domain.reservation.entity.Reservation;
import com.example.rental.domain.reservation.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByItemId(Long itemId);

    Optional<Reservation> findByUserIdAndId(Long userId, Long reservationId);

    List<Reservation> findByStatus(ReservationStatus status);

    List<Reservation> findByUserIdAndStatus(Long userId, ReservationStatus status);

    Optional<Reservation> findByQrToken(String qrToken);
}
