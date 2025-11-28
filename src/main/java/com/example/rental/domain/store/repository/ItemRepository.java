package com.example.rental.domain.store.repository;

import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.store.entity.ItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByStoreId(Long storeId);

    List<Item> findByStoreIdAndStatus(Long storeId, ItemStatus status);
}
