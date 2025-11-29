package com.example.rental.domain.store.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rental.domain.consign.entity.Consign;
import com.example.rental.domain.consign.entity.ConsignStatus;
import com.example.rental.domain.consign.repository.ConsignRepository;
import com.example.rental.domain.reservation.entity.ReservationStatus;
import com.example.rental.domain.reservation.repository.ReservationRepository;
import com.example.rental.domain.store.dto.RentalStatusResponse;
import com.example.rental.domain.store.dto.StoreInfoResponse;
import com.example.rental.domain.store.dto.StoreItemResponse;
import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.store.entity.Store;
import com.example.rental.domain.store.repository.ItemRepository;
import com.example.rental.domain.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreAdminService {

    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;
    private final ConsignRepository consignRepository;
    private final ReservationRepository reservationRepository;

    // 매장 정보 조회
    public StoreInfoResponse getStoreInfo(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));

        // 해당 매장의 모든 아이템 조회
        List<Item> items = itemRepository.findByStoreId(storeId);
        List<Long> itemIds = items.stream().map(Item::getId).toList();

        // 대여현황수: PAID 또는 IN_USE 상태인 예약
        long rentalCount = 0L;
        if (!itemIds.isEmpty()) {
            rentalCount = reservationRepository.findAll().stream()
                    .filter(r -> itemIds.contains(r.getItem().getId()))
                    .filter(r -> r.getStatus() == ReservationStatus.PAID ||
                            r.getStatus() == ReservationStatus.IN_USE)
                    .count();
        }

        // 보유물품수
        Long itemCount = (long) items.size();

        // 맡기기신청건수
        Long consignRequestCount = (long) consignRepository.findByStoreIdAndStatus(storeId, ConsignStatus.WAITING)
                .size();

        // 회수신청건수
        Long withdrawalRequestCount = (long) consignRepository.findByStoreIdAndStatus(storeId, ConsignStatus.WITHDRAWN)
                .size();

        return StoreInfoResponse.from(store, rentalCount, itemCount, consignRequestCount, withdrawalRequestCount);
    }

    // 대여 현황 조회
    public List<RentalStatusResponse> getRentedItems(Long storeId) {
        // 해당 매장의 모든 아이템 조회
        List<Item> items = itemRepository.findByStoreId(storeId);
        List<Long> itemIds = items.stream().map(Item::getId).toList();

        // 해당 아이템들의 예약 중 PAID 또는 IN_USE 상태인 것들
        if (itemIds.isEmpty()) {
            return List.of();
        }

        return reservationRepository.findAll().stream()
                .filter(r -> itemIds.contains(r.getItem().getId()))
                .filter(r -> r.getStatus() == ReservationStatus.PAID ||
                        r.getStatus() == ReservationStatus.IN_USE)
                .map(RentalStatusResponse::from)
                .collect(Collectors.toList());
    }

    // 보유 물품 조회
    public List<StoreItemResponse> getStoreItems(Long storeId) {
        List<Item> items = itemRepository.findByStoreId(storeId);
        return items.stream()
                .map(StoreItemResponse::from)
                .collect(Collectors.toList());
    }

    public List<Consign> getConsignRequests(Long storeId, ConsignStatus status) {
        List<Consign> consigns = consignRepository.findByStoreIdAndStatus(storeId, status);
        return consigns;
    }

    public Consign getConsignById(Long consignId) {
        return consignRepository.findById(consignId).orElseThrow();
    }

    @Transactional
    public void setConsignStatus(Long consignId, ConsignStatus status) {
        Consign consign = consignRepository.findById(consignId).orElseThrow();
        consign.setStatus(status);
    }
}
