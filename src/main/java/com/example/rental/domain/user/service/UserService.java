package com.example.rental.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rental.domain.user.entity.User;
import com.example.rental.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user;
    }
}
