package com.example.rental.domain.consign.controller;

import java.util.List;
import java.util.Map;

import com.example.rental.domain.consign.entity.Consign;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.rental.domain.consign.service.ConsignService;
import com.example.rental.domain.store.dto.StoreMapResponse;
import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.store.entity.Store;
import com.example.rental.domain.store.service.StoreService;
import com.example.rental.domain.user.entity.User;
import com.example.rental.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ConsignController {

    private final ConsignService consignService;
    private final UserService userService;
    private final StoreService storeService;

    // 물건 맡길 가게 목록 (목록/지도 형식 둘다 같은 api사용)
    @GetMapping("/api/consign")
    public ResponseEntity<?> getStoresToConsign() {

        List<StoreMapResponse> stores = storeService.getAllStores();
        return ResponseEntity.ok().body(stores);
    }

    // 맡길 가게 선택시 상세정보 표시
    @GetMapping("/api/consign/stores/{storeId}")
    public ResponseEntity<?> getStoresToConsign(
            @PathVariable Long storeId) {

        Store store = storeService.getStoreById(storeId);
        return ResponseEntity.ok().body(Map.of(
                "name", store.getName(),
                "lat", store.getLatitude(),
                "lon", store.getLongitude(),
                "photo_url", store.getPhotoUrl(),
                "status", store.getStatus(),
                "description", store.getDescription()));
    }

    // 물건 맡기기 신청
    @PostMapping("/api/consign")
    public ResponseEntity<?> requestConsign(@RequestBody Map<String, String> consignInfo) {

        User owner = userService.findUserById(Long.parseLong(consignInfo.get("owner_id")));
        Store store = storeService.getStoreById(Long.parseLong(consignInfo.get("store_id")));

        Item item = storeService.createItem(
                store.getId(),
                owner.getId(),
                consignInfo.get("name"),
                consignInfo.get("description"),
                consignInfo.get("photo_url"),
                Long.parseLong(consignInfo.get("fee_per_day")),
                Long.parseLong(consignInfo.get("deposit")));

        Consign consign = consignService.createConsign(owner, item, store);

        return ResponseEntity.ok().body(consign);
    }
}
