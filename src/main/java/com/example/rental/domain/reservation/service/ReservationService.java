package com.example.rental.domain.reservation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rental.domain.reservation.entity.Reservation;
import com.example.rental.domain.reservation.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> getReservationById(Long userId) {
        return reservationRepository.findByUserId(userId);
    }
}
