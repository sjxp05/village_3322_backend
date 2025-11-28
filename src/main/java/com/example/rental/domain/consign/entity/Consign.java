package com.example.rental.domain.consign.entity;

import com.example.rental.common.BaseTimeEntity;
import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.store.entity.Store;
import com.example.rental.domain.user.entity.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "consigns")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Consign extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Long totalProfit;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConsignStatus status;

    @Builder
    public Consign(User owner, Item item, Store store) {
        this.owner = owner;
        this.item = item;
        this.store = store;
        this.totalProfit = 0L;
        this.status = ConsignStatus.WAITING;
    }

    public void addProfit(Long amount) {
        this.totalProfit += amount;
    }

    public void withdraw() {
        this.status = ConsignStatus.WITHDRAWN;
    }

}
