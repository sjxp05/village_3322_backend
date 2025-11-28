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

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private Long point;

    private Double latitude;

    private Double longitude;


    @Column(name = "user_property")
    private Long property;

    @Builder
    public User(String nickname, String email, Long point, Double latitude, Double longitude) {
        this.nickname = nickname;
        this.email = email;
        this.point = point != null ? point : 0L;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public void updateLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
