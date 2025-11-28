package com.example.rental.domain.store.dto;

import com.example.rental.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class StoreResponse {

    private Long id;
    private String name;
    private String address;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static StoreResponse from(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .description(store.getDescription())
                .createdAt(store.getCreatedAt())
                .updatedAt(store.getUpdatedAt())
                .build();
    }
}
