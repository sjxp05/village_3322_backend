package com.example.rental.domain.store.dto;

import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.store.entity.ItemStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ItemDetailResponse {

    private Long id;
    private Long storeId;
    private String name;
    private Long pricePerHour;
    private Long deposit;
    private ItemStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ItemDetailResponse from(Item item) {
        return ItemDetailResponse.builder()
                .id(item.getId())
                .storeId(item.getStore().getId())
                .name(item.getName())
                .pricePerHour(item.getPricePerHour())
                .deposit(item.getDeposit())
                .status(item.getStatus())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }
}
