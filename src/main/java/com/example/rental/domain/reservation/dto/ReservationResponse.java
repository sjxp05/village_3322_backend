package com.example.rental.domain.reservation.dto;

import com.example.rental.domain.reservation.entity.Reservation;
import com.example.rental.domain.reservation.entity.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationResponse {

    private Long id;
    private Long userId;
    private Long itemId;
    private String itemName;
    private Long initialPaidFee;
    private String qrToken;
    private ReservationStatus status;
    private LocalDateTime createdAt;

    public static ReservationResponse from(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .userId(reservation.getUser().getId())
                .itemId(reservation.getItem().getId())
                .itemName(reservation.getItem().getName())
                .initialPaidFee(reservation.getInitialPaidFee())
                .qrToken(reservation.getQrToken())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}
