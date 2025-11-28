package com.example.rental.domain.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.store.entity.ItemStatus;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByStoreId(Long storeId);

    List<Item> findByStatus(ItemStatus status);

    List<Item> findByStoreIdAndStatus(Long storeId, ItemStatus status);
}
