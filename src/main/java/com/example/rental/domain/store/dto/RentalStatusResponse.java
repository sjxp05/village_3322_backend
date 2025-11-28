package com.example.rental.domain.store.dto;

import com.example.rental.domain.reservation.entity.Reservation;
import com.example.rental.domain.reservation.entity.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RentalStatusResponse {

    private Long reservationId;
    private String itemName;
    private Long totalFee;
    private ReservationStatus status;
    private String photoUrl;

    public static RentalStatusResponse from(Reservation reservation) {
        return RentalStatusResponse.builder()
                .reservationId(reservation.getId())
                .itemName(reservation.getItem().getName())
                .totalFee(reservation.getInitialPaidFee())
                .status(reservation.getStatus())
                .photoUrl(reservation.getItem().getPhotoUrl())
                .build();
    }
}