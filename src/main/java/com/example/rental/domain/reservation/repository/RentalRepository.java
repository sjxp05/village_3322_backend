package com.example.rental.domain.reservation.repository;

import com.example.rental.domain.reservation.entity.Rental;
import com.example.rental.domain.reservation.entity.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByStatus(RentalStatus status);

    Optional<Rental> findByReservationId(Long reservationId);
}
