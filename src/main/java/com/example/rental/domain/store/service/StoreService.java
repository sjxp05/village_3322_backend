package com.example.rental.domain.store.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rental.domain.consign.service.ConsignService;
import com.example.rental.domain.store.dto.ItemDetailResponse;
import com.example.rental.domain.store.dto.StoreMapResponse;
import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.store.entity.ItemStatus;
import com.example.rental.domain.store.entity.Store;
import com.example.rental.domain.store.repository.ItemRepository;
import com.example.rental.domain.store.repository.StoreRepository;
import com.example.rental.domain.user.entity.User;
import com.example.rental.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ConsignService consignService;

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

    public Store getStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));
    }

    @Transactional
    public Item createItem(Long storeId, Long ownerId, String name, String description,
            String photoUrl, Long feePerHour, Long deposit) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));

        User owner = null;
        if (ownerId != null) {
            owner = userRepository.findById(ownerId)
                    .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        }

        Item item = Item.builder()
                .store(store)
                .owner(owner)
                .name(name)
                .description(description)
                .photoUrl(photoUrl)
                .feePerHour(feePerHour)
                .deposit(deposit)
                .status(ItemStatus.AVAILABLE)
                .build();

        Item savedItem = itemRepository.save(item);

        // If it's a consigned item (has owner), create consign record
        if (owner != null) {
            consignService.createConsign(owner, savedItem);
        }

        return savedItem;
    }
}
