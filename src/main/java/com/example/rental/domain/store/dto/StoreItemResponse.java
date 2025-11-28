package com.example.rental.domain.store.dto;

import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.store.entity.ItemStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreItemResponse {

    private Long itemId;
    private String name;
    private Long feePerDay;
    private Long deposit;
    private ItemStatus status;
    private String photoUrl;

    public static StoreItemResponse from(Item item) {
        return StoreItemResponse.builder()
                .itemId(item.getId())
                .name(item.getName())
                .feePerDay(item.getFeePerDay())
                .deposit(item.getDeposit())
                .status(item.getStatus())
                .photoUrl(item.getPhotoUrl())
                .build();
    }
}