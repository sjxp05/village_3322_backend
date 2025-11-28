package com.example.rental.domain.store.dto;

import com.example.rental.domain.store.entity.Store;
import com.example.rental.domain.store.entity.StoreStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreInfoResponse {

    private Long storeId;
    private String name;
    private String photoUrl;
    private StoreStatus status;
    private Long totalRevenue;        // 총수익
    private Long rentalCount;         // 대여현황수
    private Long itemCount;           // 보유물품수
    private Long consignRequestCount; // 맡기기신청건수
    private Long withdrawalRequestCount; // 회수신청건수

    public static StoreInfoResponse from(
            Store store,
            Long rentalCount,
            Long itemCount,
            Long consignRequestCount,
            Long withdrawalRequestCount
    ) {
        return StoreInfoResponse.builder()
                .storeId(store.getId())
                .name(store.getName())
                .photoUrl(store.getPhotoUrl())
                .status(store.getStatus())
                .totalRevenue(store.getProperty() != null ? store.getProperty() : 0L)
                .rentalCount(rentalCount)
                .itemCount(itemCount)
                .consignRequestCount(consignRequestCount)
                .withdrawalRequestCount(withdrawalRequestCount)
                .build();
    }
}