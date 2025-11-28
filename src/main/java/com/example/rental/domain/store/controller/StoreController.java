package com.example.rental.domain.store.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.rental.common.ApiResponse;
import com.example.rental.domain.store.dto.ItemDetailResponse;
import com.example.rental.domain.store.dto.StoreMapResponse;
import com.example.rental.domain.store.service.StoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/api/items")
    public ResponseEntity<ApiResponse<List<StoreMapResponse>>> getAllStores() {
        List<StoreMapResponse> stores = storeService.getAllStores();
        return ResponseEntity.ok(ApiResponse.success(stores));
    }

    @GetMapping("/api/items/stores/{storeId}")
    public ResponseEntity<ApiResponse<List<ItemDetailResponse>>> getItemsByStore(@PathVariable Long storeId) {
        List<ItemDetailResponse> items = storeService.getItemsByStore(storeId);
        return ResponseEntity.ok(ApiResponse.success(items));
    }
}
