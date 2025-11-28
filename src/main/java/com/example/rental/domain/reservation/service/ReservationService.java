package com.example.rental.domain.reservation.service;

import com.example.rental.domain.reservation.dto.PaymentFinalizeResponse;
import com.example.rental.domain.reservation.dto.ReservationCreateRequest;
import com.example.rental.domain.reservation.dto.ReservationResponse;
import com.example.rental.domain.reservation.entity.Reservation;
import com.example.rental.domain.reservation.entity.ReservationStatus;
import com.example.rental.domain.reservation.repository.ReservationRepository;
import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.store.entity.ItemStatus;
import com.example.rental.domain.store.repository.ItemRepository;
import com.example.rental.domain.user.entity.User;
import com.example.rental.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.rental.domain.store.entity.ItemStatus.RENTED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository; // 유저 조회를 위해 필요
    private final ItemRepository itemRepository;

    // 사용자의 모든 예약 내역 불러오기
    public List<ReservationResponse> getReservationByUserId(Long userId) {
        return reservationRepository.findByUserId(userId).stream().map(ReservationResponse::from).toList();
    }

    // 사용자의 예약 추가
    @Transactional
    public ReservationResponse createReservation(ReservationCreateRequest request) {
        // 1. 유저 조회
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 2. 아이템 조회
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        if (item.getStatus() != ItemStatus.AVAILABLE) {
            throw new IllegalArgumentException("Item is already available");
        }
        // 3. 결제 금액 계산 (아이템 시간당 가격 * 대여 시간)
        // Item 엔티티에 getFeePerHr() 메서드가 있다고 가정합니다. (ERD 기준 fee_per_hr)
        long totalPrice = item.getFeePerDay() * request.getUsageDays();

        // 4. 예약 엔티티 생성 (빌더 패턴 사용)
        Reservation reservation = Reservation.builder()
                .user(user)
                .item(item)
                .usageDays(request.getUsageDays())
                .initialPaidFee(totalPrice) // 계산된 금액 주입
                .status(ReservationStatus.PAID) // 초기 상태는 결제 완료(PAID)로 가정
                .build();

        //4-1 아이템 상태 변환
        item.setStatus(ItemStatus.RENTED);

        // 5. 저장
        Reservation savedReservation = reservationRepository.save(reservation);

        // 6. DTO 변환 후 반환
        return ReservationResponse.from(savedReservation);

    }

    // 사용 시작
    // 상태가 in use로 바뀜
    @Transactional
    public ReservationResponse startReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        reservation.startUsage();

        return ReservationResponse.from(reservation);
    }

    // 대여 연장
    // usage_day 갱신
    @Transactional
    public ReservationResponse extendReservation(Long reservationId, Long additionalDays) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        reservation.extendUsageDays(additionalDays);

        return ReservationResponse.from(reservation);
    }

    // 반납 위한 개별 qr
    public ReservationResponse getReturnQr(Long userId, Long reservationId) {
        Reservation reservation = reservationRepository.findByUserIdAndId(userId, reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        return ReservationResponse.from(reservation);
    }

    // 반납하기
    // endedAt이 찍히고, 상태가 returned
    @Transactional
    public ReservationResponse returnItem(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        reservation.markReturned();

        return ReservationResponse.from(reservation);
    }

    // 최종 결제 및 환불
    @Transactional
    public PaymentFinalizeResponse finalizePayment(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (reservation.getStartedAt() == null || reservation.getEndedAt() == null) {
            throw new IllegalStateException("대여 시작 시간과 종료 시간이 모두 필요합니다.");
        }

        // 실제 사용 시간 계산 (시간 단위)
        long actualMinutes = java.time.Duration.between(
                reservation.getStartedAt(),
                reservation.getEndedAt()).toMinutes();
        long actualDays = (actualMinutes + 59) / 60; // 올림 처리

        // 실제 요금 계산
        long actualFee = actualDays * reservation.getItem().getFeePerDay();

        // actualPaidFee 설정
        reservation.setActualPaidFee(actualFee);

        // 환불 또는 추가 결제 금액 계산
        long difference = reservation.getInitialPaidFee() - actualFee;

        // PaymentFinalizeResponse 생성 및 반환
        return PaymentFinalizeResponse.from(reservation, actualFee, actualDays, difference);
    }

}
