package com.example.rental.domain.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.rental.common.ApiResponse;
import com.example.rental.domain.store.dto.ItemDetailResponse;
import com.example.rental.domain.store.dto.StoreMapResponse;
import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.store.entity.Store;
import com.example.rental.domain.store.service.StoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // 근처 아이템 목록 모두 표시 (일단 모든 데이터 표시. 랜덤)
    @GetMapping("/api/items")
    public ResponseEntity<ApiResponse<List<ItemDetailResponse>>> getAllItems() {
        List<ItemDetailResponse> items = storeService.getAllAvailableItems();
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    // 아이템 한 개 상세정보
    @GetMapping("/api/items/{itemId}")
    public ResponseEntity<?> getItemInfo(@PathVariable Long itemId) {
        Item item = storeService.getItemById(itemId);
        Store store = item.getStore();
        List<ItemDetailResponse> storeOtherItems = storeService.getItemsByStore(item.getStore());

        return ResponseEntity.ok().body(Map.of(
                "name", item.getName(),
                "photo_url", item.getPhotoUrl(),
                "fee_per_hour", item.getFeePerHour(),
                "deposit", item.getDeposit(),
                "store_name", store.getName(),
                "store_lat", store.getLatitude(),
                "store_lon", store.getLongitude(),
                "store_other_items", storeOtherItems));
    }

    // 지도에 매장 위치 모두 표시
    @GetMapping("/api/items/map")
    public ResponseEntity<ApiResponse<List<StoreMapResponse>>> getAllStores() {
        List<StoreMapResponse> stores = storeService.getAllStores();
        return ResponseEntity.ok(ApiResponse.success(stores));
    }

    // 매장 클릭시 상품 목록 모두 표시
    @GetMapping("/api/items/stores/{storeId}")
    public ResponseEntity<ApiResponse<List<ItemDetailResponse>>> getItemsByStore(@PathVariable Long storeId) {
        List<ItemDetailResponse> items = storeService.getItemsByStore(storeId);
        return ResponseEntity.ok(ApiResponse.success(items));
    }
}
