package com.example.rental.domain.consign.dto;

import com.example.rental.domain.consign.entity.Consign;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsignResponse {

    private Long consignId;
    private String ownerNickname;
    private String photoUrl;

    public static ConsignResponse from(Consign consign) {
        return ConsignResponse.builder()
                .consignId(consign.getId())
                .ownerNickname(consign.getOwner().getNickname())
                .photoUrl(consign.getItem().getPhotoUrl())
                .build();
    }
}
