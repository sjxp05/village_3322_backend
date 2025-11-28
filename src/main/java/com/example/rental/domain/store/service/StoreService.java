package com.example.rental.domain.store.service;

import com.example.rental.domain.store.dto.ItemDetailResponse;
import com.example.rental.domain.store.dto.StoreMapResponse;
import com.example.rental.domain.store.entity.ItemStatus;
import com.example.rental.domain.store.repository.ItemRepository;
import com.example.rental.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;

    public List<StoreMapResponse> getAllStores() {
        return storeRepository.findAll().stream()
                .map(StoreMapResponse::from)
                .toList();
    }

    public List<ItemDetailResponse> getItemsByStore(Long storeId) {
        return itemRepository.findByStoreIdAndStatus(storeId, ItemStatus.AVAILABLE).stream()
                .map(ItemDetailResponse::from)
                .toList();
    }
}
