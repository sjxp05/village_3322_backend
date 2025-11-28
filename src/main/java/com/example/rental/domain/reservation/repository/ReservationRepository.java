package com.example.rental.domain.reservation.repository;

import com.example.rental.domain.reservation.entity.Reservation;
import com.example.rental.domain.reservation.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByStoreId(Long storeId);

    List<Reservation> findByStatus(ReservationStatus status);
}
