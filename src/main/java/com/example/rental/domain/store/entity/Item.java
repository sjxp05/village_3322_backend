package com.example.rental.domain.store.entity;

import com.example.rental.common.BaseTimeEntity;
import com.example.rental.domain.user.entity.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String photoUrl;

    @Column(nullable = false)
    private Long feePerDay;

    @Column(nullable = false)
    private Long feePerHour;

    @Column(nullable = false)
    private Long deposit;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemStatus status;

    @Builder
    public Item(Store store, User owner, String name, String description, String photoUrl,
                Long feePerDay, Long feePerHour, Long deposit, Integer quantity, ItemStatus status) {
        this.store = store;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
        this.feePerDay = feePerDay;
        this.feePerHour = feePerHour;
        this.deposit = deposit;
        this.quantity = quantity != null ? quantity : 1;
        this.status = status != null ? status : ItemStatus.AVAILABLE;
    }

    public boolean isConsignedItem() {
        return this.owner != null;
    }

    public void decreaseQuantity() {
        if (this.quantity <= 0) {
            throw new IllegalStateException("No available quantity");
        }
        this.quantity--;
        if (this.quantity == 0) {
            this.status = ItemStatus.RENTED;
        }
    }

    public void increaseQuantity() {
        this.quantity++;
        if (this.status == ItemStatus.RENTED) {
            this.status = ItemStatus.AVAILABLE;
        }
    }
}
