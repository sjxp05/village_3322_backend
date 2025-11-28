package com.example.rental.domain.user.dto;

import com.example.rental.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String nickname;
    private Long point;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .point(user.getPoint())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
