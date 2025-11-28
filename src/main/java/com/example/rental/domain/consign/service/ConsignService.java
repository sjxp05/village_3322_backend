package com.example.rental.domain.consign.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rental.domain.consign.entity.Consign;
import com.example.rental.domain.consign.entity.ConsignStatus;
import com.example.rental.domain.consign.repository.ConsignRepository;
import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.store.entity.Store;
import com.example.rental.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsignService {

    private final ConsignRepository consignRepository;

    @Transactional
    public Consign createConsign(User owner, Item item, Store store) {
        Consign consign = Consign.builder()
                .owner(owner)
                .item(item)
                .store(store)
                .build();
        return consignRepository.save(consign);
    }

    public List<Consign> getConsignsByOwner(Long ownerId) {
        return consignRepository.findByOwnerId(ownerId);
    }

    public List<Consign> getActiveConsignsByOwner(Long ownerId) {
        return consignRepository.findByOwnerIdAndStatus(ownerId, ConsignStatus.ACTIVE);
    }

    @Transactional
    public void addProfitToConsign(Long itemId, Long profit) {
        consignRepository.findByItemId(itemId)
                .ifPresent(consign -> consign.addProfit(profit));
    }

    @Transactional
    public void withdrawConsign(Long consignId) {
        Consign consign = consignRepository.findById(consignId)
                .orElseThrow(() -> new IllegalArgumentException("Consign not found"));
        consign.withdraw();
    }
}
