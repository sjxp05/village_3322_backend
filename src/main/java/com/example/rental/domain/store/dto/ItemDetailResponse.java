package com.example.rental.domain.store.dto;

import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.store.entity.ItemStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemDetailResponse {

    private Long id;
    private Long storeId;
    private Long ownerId;
    private String name;
    private String description;
    private String photoUrl;
    private Long feePerDay;
    private Long deposit;
    private Integer quantity;
    private ItemStatus status;
    private boolean isConsignedItem;

    public static ItemDetailResponse from(Item item) {
        return ItemDetailResponse.builder()
                .id(item.getId())
                .storeId(item.getStore().getId())
                .ownerId(item.getOwner() != null ? item.getOwner().getId() : null)
                .name(item.getName())
                .description(item.getDescription())
                .photoUrl(item.getPhotoUrl())
                .feePerDay(item.getFeePerDay())
                .deposit(item.getDeposit())
                .status(item.getStatus())
                .isConsignedItem(item.isConsignedItem())
                .build();
    }
}
