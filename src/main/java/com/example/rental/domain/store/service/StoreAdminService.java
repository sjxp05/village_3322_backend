package com.example.rental.domain.store.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rental.domain.consign.entity.Consign;
import com.example.rental.domain.consign.entity.ConsignStatus;
import com.example.rental.domain.consign.repository.ConsignRepository;
import com.example.rental.domain.store.repository.ItemRepository;
import com.example.rental.domain.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreAdminService {

    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;
    private final ConsignRepository consignRepository;

    public List<Consign> getConsignRequests(Long storeId, ConsignStatus status) {
        List<Consign> consigns = consignRepository.findByStoreIdAndStatus(storeId, status);
        return consigns;
    }

    public Consign getConsignById(Long consignId) {
        return consignRepository.findById(consignId).orElseThrow();
    }

    @Transactional
    public void setConsignStatus(Long consignId, ConsignStatus status) {
        Consign consign = consignRepository.findById(consignId).orElseThrow();
        consign.setStatus(status);
    }
}
