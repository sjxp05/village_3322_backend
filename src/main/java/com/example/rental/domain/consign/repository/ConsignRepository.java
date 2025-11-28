package com.example.rental.domain.consign.repository;

import com.example.rental.domain.consign.entity.Consign;
import com.example.rental.domain.consign.entity.ConsignStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConsignRepository extends JpaRepository<Consign, Long> {

    List<Consign> findByOwnerId(Long ownerId);

    List<Consign> findByOwnerIdAndStatus(Long ownerId, ConsignStatus status);

    Optional<Consign> findByItemId(Long itemId);
}
