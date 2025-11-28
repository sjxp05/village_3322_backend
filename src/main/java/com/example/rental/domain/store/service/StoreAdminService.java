package com.example.rental.domain.store.service;

import com.example.rental.domain.consign.repository.ConsignRepository;
import com.example.rental.domain.store.repository.ItemRepository;
import com.example.rental.domain.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
public class StoreAdminService {

    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;
    private final ConsignRepository consignRepository;

}
