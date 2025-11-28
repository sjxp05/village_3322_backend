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
    private Long feePerHour;

    @Column(nullable = false)
    private Long deposit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemStatus status;

    @Builder
    public Item(Store store, User owner, String name, String description, String photoUrl,
                 Long feePerHour, Long deposit, ItemStatus status) {
        this.store = store;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
        this.feePerHour = feePerHour;
        this.deposit = deposit;
        this.status = status != null ? status : ItemStatus.AVAILABLE;
    }

    public boolean isConsignedItem() {
        return this.owner != null;
    }

    public void rent() {
        if(this.status == ItemStatus.AVAILABLE){
             this.status = ItemStatus.RENTED;
        }
    }

    public void setStatus(){
        if(this.status == ItemStatus.RENTED){
            this.status = ItemStatus.AVAILABLE;
        }
    }
}
