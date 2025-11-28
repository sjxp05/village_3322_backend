package com.example.rental.domain.store.controller;

import com.example.rental.common.ApiResponse;
import com.example.rental.domain.store.dto.ItemDetailResponse;
import com.example.rental.domain.store.dto.StoreMapResponse;
import com.example.rental.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StoreMapResponse>>> getAllStores() {
        List<StoreMapResponse> stores = storeService.getAllStores();
        return ResponseEntity.ok(ApiResponse.success(stores));
    }

    @GetMapping("/{storeId}/items")
    public ResponseEntity<ApiResponse<List<ItemDetailResponse>>> getItemsByStore(@PathVariable Long storeId) {
        List<ItemDetailResponse> items = storeService.getItemsByStore(storeId);
        return ResponseEntity.ok(ApiResponse.success(items));
    }
}
