package com.example.rental.domain.consign.controller;

import java.util.Map;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.rental.domain.consign.service.ConsignService;
import com.example.rental.domain.store.entity.Store;
import com.example.rental.domain.store.service.StoreService;
import com.example.rental.domain.user.entity.User;
import com.example.rental.domain.user.service.UserService;

@RestController
@RequestMapping("/api/consign")
@RequiredArgsConstructor
public class ConsignController {

    private final ConsignService consignService;
    private final UserService userService;
    private final StoreService storeService;

    // 물건 맡기기 신청
    @PostMapping("/api/consign")
    public ResponseEntity<?> requestConsign(@RequestBody Map<String, String> consignInfo) {
        User owner = userService.findUserById(consignInfo.get("owner_id"));
        Store store = storeService.findById(consignInfo.get("store_id"));

        consignService.createConsign(owner, store);
    }

}
