package com.example.rental.domain.reservation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rental.common.ApiResponse;
import com.example.rental.domain.reservation.dto.ReservationResponse;
import com.example.rental.domain.reservation.entity.Reservation;
import com.example.rental.domain.reservation.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getAllReservations(
            @RequestParam("userid") Long userId) {
        List<Reservation> reservations = reservationService.getReservationById(userId);

        List<ReservationResponse> reservationResps = reservations.stream().map(ReservationResponse::from).toList();

        return ResponseEntity.ok(ApiResponse.success(reservationResps));
    }
}
