package com.example.rental.domain.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.rental.domain.consign.entity.Consign;
import com.example.rental.domain.consign.service.ConsignService;
import com.example.rental.domain.store.entity.Item;
import com.example.rental.domain.user.entity.User;
import com.example.rental.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ConsignService consignService;

    @GetMapping("/{userId}/settings")
    public ResponseEntity<?> getSettings(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        List<Item> consignedItems = consignService.getActiveConsignsByOwner(userId).stream().map(Consign::getItem)
                .toList();
        Map<String, List<?>> consignHistory = new HashMap<>();

        for (Item i : consignedItems) {
            consignHistory.put(i.getStore().getName(), List.of(i.getId(), i.getName(), i.getFeePerDay()));
        }

        return ResponseEntity.ok().body(Map.of(
                "nickname", user.getNickname(),
                "point", user.getPoint(),
                "consign_history", consignHistory));
    }
}
