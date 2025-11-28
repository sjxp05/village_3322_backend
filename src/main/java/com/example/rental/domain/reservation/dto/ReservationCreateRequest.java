package com.example.rental.domain.reservation.dto;

import com.example.rental.domain.reservation.entity.Reservation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReservationCreateRequest {
    private Long userId;
    private Long itemId;
    private Long usageDays;

}
