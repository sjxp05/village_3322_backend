package com.example.rental.domain.reservation.dto;

import com.example.rental.domain.reservation.entity.Reservation;
import com.example.rental.domain.reservation.entity.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentFinalizeResponse {

    private Long reservationId;
    private Long userId;
    private Long itemId;
    private String itemName;
    private ReservationStatus status;

    // 결제 관련 정보
    private Long initialPaidFee; // 초기 결제 금액
    private Long actualPaidFee; // 실제 결제 금액
    private Long difference; // 차액 (양수: 환불, 음수: 추가 결제)
    private PaymentType paymentType; // 결제 타입
    private String message; // 안내 메시지

    // 시간 정보
    private Long usageDays; // 예약한 사용 시간
    private Long actualUsageDays; // 실제 사용 시간
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    public enum PaymentType {
        REFUND, // 환불
        ADDITIONAL, // 추가 결제
        NONE // 차액 없음
    }

    public static PaymentFinalizeResponse from(
            Reservation reservation,
            Long actualFee,
            Long actualDays,
            Long difference) {
        PaymentType paymentType;
        String message;

        if (difference > 0) {
            paymentType = PaymentType.REFUND;
            message = "환불 금액: " + difference + "원";
        } else if (difference < 0) {
            paymentType = PaymentType.ADDITIONAL;
            message = "추가 결제 금액: " + Math.abs(difference) + "원";
        } else {
            paymentType = PaymentType.NONE;
            message = "차액이 없습니다.";
        }

        return PaymentFinalizeResponse.builder()
                .reservationId(reservation.getId())
                .userId(reservation.getUser().getId())
                .itemId(reservation.getItem().getId())
                .itemName(reservation.getItem().getName())
                .status(reservation.getStatus())
                .initialPaidFee(reservation.getInitialPaidFee())
                .actualPaidFee(actualFee)
                .difference(difference)
                .paymentType(paymentType)
                .message(message)
                .usageDays(reservation.getUsageDays())
                .actualUsageDays(actualDays)
                .startedAt(reservation.getStartedAt())
                .endedAt(reservation.getEndedAt())
                .build();
    }
}
