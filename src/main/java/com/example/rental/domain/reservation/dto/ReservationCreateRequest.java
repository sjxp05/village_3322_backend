package com.example.rental.domain.reservation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationCreateRequest {
    private Long userId;
    private Long itemId;
    private Long usageDays;

}
