package com.example.rental.domain.reservation.service;

import com.example.rental.domain.consign.service.ConsignService;
import com.example.rental.domain.reservation.entity.*;
import com.example.rental.domain.reservation.repository.RentalRepository;
import com.example.rental.domain.reservation.repository.ReservationRepository;
import com.example.rental.domain.store.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalService {

    private final ReservationRepository reservationRepository;
    private final RentalRepository rentalRepository;
    private final ConsignService consignService;

    /**
     * QR Scan 1: Pickup - Create rental record and start usage
     */
    @Transactional
    public Rental processPickup(String qrToken, int rentalHours) {
        Reservation reservation = reservationRepository.findByQrToken(qrToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid QR token"));

        if (reservation.getStatus() != ReservationStatus.PAID) {
            throw new IllegalStateException("Reservation is not in PAID status");
        }

        // Update reservation status
        reservation.startUsage();

        // Create rental record
        LocalDateTime timeLimit = LocalDateTime.now().plusHours(rentalHours);
        Rental rental = Rental.builder()
                .reservation(reservation)
                .timeLimit(timeLimit)
                .build();

        return rentalRepository.save(rental);
    }

    /**
     * QR Scan 2: Return - Complete rental and calculate fees
     */
    @Transactional
    public Rental processReturn(String qrToken) {
        Reservation reservation = reservationRepository.findByQrToken(qrToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid QR token"));

        if (reservation.getStatus() != ReservationStatus.IN_USE) {
            throw new IllegalStateException("Reservation is not in IN_USE status");
        }

        Rental rental = rentalRepository.findByReservationId(reservation.getId())
                .orElseThrow(() -> new IllegalStateException("Rental record not found"));

        // Calculate actual fee
        Long actualFee = calculateActualFee(rental, reservation.getItem());

        // Complete return
        rental.completeReturn(actualFee);
        reservation.markReturned();

        // Add profit to consign if it's a consigned item
        Item item = reservation.getItem();
        if (item.isConsignedItem()) {
            // Owner gets 70% of the profit (example split)
            Long ownerProfit = (long) (actualFee * 0.7);
            consignService.addProfitToConsign(item.getId(), ownerProfit);

            // Add points to owner
            item.getOwner().addPoint(ownerProfit);
        }

        return rental;
    }

    private Long calculateActualFee(Rental rental, Item item) {
        Duration duration = Duration.between(rental.getStartedAt(), LocalDateTime.now());
        long hours = duration.toHours();
        long days = duration.toDays();

        // Calculate based on day or hour rate (whichever is cheaper for user)
        Long hourlyTotal = hours * item.getFeePerHour();

        return Math.min(hourlyTotal);
    }

    public Rental getRentalByReservationId(Long reservationId) {
        return rentalRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Rental not found"));
    }
}
