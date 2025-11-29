package com.example.rental.domain.reservation.controller;

import com.example.rental.common.ApiResponse;
import com.example.rental.domain.reservation.dto.PaymentFinalizeResponse;
import com.example.rental.domain.reservation.dto.ReservationCreateRequest;
import com.example.rental.domain.reservation.dto.ReservationResponse;
import com.example.rental.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 사용자의 모든 예약 내역 불러오기
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getAllReservations(
            @PathVariable Long userId) {
        List<ReservationResponse> reservations = reservationService.getReservationByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(reservations));
    }

    // 사용자의 결제한 예약 (PAID) - 대여 예정
    @GetMapping("/{userId}/paid")
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getPaidReservations(
            @PathVariable Long userId) {
        List<ReservationResponse> reservations = reservationService.getPaidReservations(userId);
        return ResponseEntity.ok(ApiResponse.success(reservations));
    }

    // 사용자의 대여 중인 예약 (IN_USE)
    @GetMapping("/{userId}/in-use")
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getInUseReservations(
            @PathVariable Long userId) {
        List<ReservationResponse> reservations = reservationService.getInUseReservations(userId);
        return ResponseEntity.ok(ApiResponse.success(reservations));
    }

    // 사용자의 반납한 예약 (RETURNED)
    @GetMapping("/{userId}/returned")
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getReturnedReservations(
            @PathVariable Long userId) {
        List<ReservationResponse> reservations = reservationService.getReturnedReservations(userId);
        return ResponseEntity.ok(ApiResponse.success(reservations));
    }

    // 사용자의 반납 위한 개별 qr
    @GetMapping("/{userId}/{reservationId}")
    public ResponseEntity<ApiResponse<ReservationResponse>> getReservationById(
            @PathVariable Long userId,
            @PathVariable Long reservationId) {
        ReservationResponse reservation = reservationService.getReturnQr(userId, reservationId);
        return ResponseEntity.ok(ApiResponse.success(reservation));
    }

    // 에약하기 = 결제하기...
    @PostMapping("/payment")
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(
            @RequestBody ReservationCreateRequest request // DTO로 받음
    ) {
        ReservationResponse response = reservationService.createReservation(request);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 사용 시작 - qr 코드 클릭하면 작동!
    @PostMapping("/{reservationId}/start")
    public ResponseEntity<ApiResponse<ReservationResponse>> startReservation(
            @PathVariable Long reservationId) {
        ReservationResponse response = reservationService.startReservation(reservationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 대여 연장
    @PatchMapping("/{reservationId}/extend")
    public ResponseEntity<ApiResponse<ReservationResponse>> extendReservation(
            @PathVariable Long reservationId,
            @RequestParam Long additionalDays) {
        ReservationResponse response = reservationService.extendReservation(reservationId, additionalDays);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 반납하기 - 마찬가지로 qr 클릭하면 반납이 됨
    @PostMapping("/{reservationId}/return")
    public ResponseEntity<ApiResponse<ReservationResponse>> returnItem(
            @PathVariable Long reservationId) {
        ReservationResponse response = reservationService.returnItem(reservationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 최종 결제 및 환불 -정산 버튼 눌렸을 시에 작동함.
    @PostMapping("/{reservationId}/finalize")
    public ResponseEntity<ApiResponse<PaymentFinalizeResponse>> finalizePayment(
            @PathVariable Long reservationId) {
        PaymentFinalizeResponse response = reservationService.finalizePayment(reservationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
