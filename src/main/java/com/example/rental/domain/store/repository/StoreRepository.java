package com.example.rental.domain.store.repository;

import com.example.rental.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByNameContaining(String name);
}
