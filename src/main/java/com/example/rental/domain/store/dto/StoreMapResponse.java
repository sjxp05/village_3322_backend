package com.example.rental.domain.store.dto;

import com.example.rental.domain.store.entity.Store;
import com.example.rental.domain.store.entity.StoreCategory;
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

    public static StoreMapResponse from(Store store) {
        return StoreMapResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .lat(store.getLatitude())
                .lon(store.getLongitude())
                .category(store.getCategory())
                .build();
    }
}
