package com.example.rental.domain.reservation.controller;

import com.example.rental.common.ApiResponse;
import com.example.rental.domain.reservation.dto.ReservationResponse;
import com.example.rental.domain.reservation.service.ReservationService;
import com.example.rental.domain.store.dto.StoreMapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;


    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getAllReservations(
            @RequestParam("userid") Long userId
    ) {
        List<ReservationResponse> reservations = reservationService.getReservationById(userId);

        return ResponseEntity.ok(ApiResponse.success(reservations));
    }
}
