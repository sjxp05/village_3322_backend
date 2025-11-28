package com.example.rental.domain.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rental.domain.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByNameContaining(String name);
}
