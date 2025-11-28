package com.example.rental.domain.consign.controller;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.rental.domain.consign.service.ConsignService;
import com.example.rental.domain.store.dto.StoreMapResponse;
import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.store.entity.Store;
import com.example.rental.domain.store.service.StoreService;
import com.example.rental.domain.user.entity.User;
import com.example.rental.domain.user.service.UserService;

@RestController
@RequestMapping("/api/consign")
@RequiredArgsConstructor
public class ConsignController {

    private final ConsignService consignService;
    private final UserService userService;
    private final StoreService storeService;

    // 물건 맡길 가게 목록
    @GetMapping("/api/consign")
    public ResponseEntity<?> getStoresToConsign(
            @RequestParam(name = "lat") Double latitude,
            @RequestParam(name = "lon") Double longitude) {

        List<StoreMapResponse> stores = storeService.getStoresAroundRadius(latitude, longitude, 1);
        return ResponseEntity.ok().body(stores);
    }

    @GetMapping("/api/consign/stores/{store_id}")
    public ResponseEntity<?> getStoresToConsign(@PathVariable Long storeId) {
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

        Item item = storeService.createItem(
                Long.parseLong(consignInfo.get("store_id")),
                owner.getId(),
                consignInfo.get("name"),
                consignInfo.get("description"),
                consignInfo.get("photo_url"),
                Long.parseLong(consignInfo.get("fee_per_hour")),
                Long.parseLong(consignInfo.get("deposit")));

        consignService.createConsign(owner, item);
    }
}
