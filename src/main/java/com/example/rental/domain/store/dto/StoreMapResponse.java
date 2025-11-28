package com.example.rental.domain.store.dto;

import com.example.rental.domain.store.entity.Store;
import com.example.rental.domain.store.entity.StoreCategory;
import com.example.rental.domain.store.entity.StoreStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreMapResponse {

    private Long id;
    private String name;
    private Double lat;
    private Double lon;
    private StoreCategory category;
    private StoreStatus status;

    public static StoreMapResponse from(Store store) {
        return StoreMapResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .lat(store.getLatitude())
                .lon(store.getLongitude())
                .status(store.getStatus())
                .build();
    }
}
