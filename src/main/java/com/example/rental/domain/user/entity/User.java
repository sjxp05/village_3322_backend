package com.example.rental.domain.user.entity;

import com.example.rental.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Long point;

    @Builder
    public User(String nickname, Long point) {
        this.nickname = nickname;
        this.point = point != null ? point : 0L;
    }

    public void addPoint(Long amount) {
        this.point += amount;
    }

    public void deductPoint(Long amount) {
        if (this.point < amount) {
            throw new IllegalArgumentException("Insufficient points");
        }
        this.point -= amount;
    }
}
