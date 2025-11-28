package com.example.rental.domain.store.entity;

import com.example.rental.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long pricePerHour;

    @Column(nullable = false)
    private Long deposit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemStatus status;

    @Builder
    public Item(Store store, String name, Long pricePerHour, Long deposit, ItemStatus status) {
        this.store = store;
        this.name = name;
        this.pricePerHour = pricePerHour;
        this.deposit = deposit;
        this.status = status != null ? status : ItemStatus.AVAILABLE;
    }

    public void rent() {
        this.status = ItemStatus.RENTED;
    }

    public void returnItem() {
        this.status = ItemStatus.AVAILABLE;
    }
}
