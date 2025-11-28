package com.example.rental.domain.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.rental.domain.consign.entity.Consign;
import com.example.rental.domain.consign.entity.ConsignStatus;
import com.example.rental.domain.store.service.StoreAdminService;

import lombok.RequiredArgsConstructor;
import main.java.com.example.rental.domain.consign.dto.ConsignResponse;

@RestController
@RequiredArgsConstructor
public class StoreAdminController {

    public final StoreAdminService StoreAdminService;

    // 맡기기 신청 메뉴
    @GetMapping("/api/admin/{storeId}/requests/consign")
    public ResponseEntity<?> getAllConsignRequests(@PathVariable Long storeId) {
        List<Consign> consigns = storeAdminService.getConsignRequests(storeId, ConsignStatus.WAITING);
        List<ConsignResponse> consignResps = consigns.stream().map(ConsignResponse::from).toList();

        return ResponseEntity.ok().body(consignResps);
    }

    // 맡기기 신청한 항목 1개 클릭시
    @GetMapping("/api/admin/{storeId}/requests/consign/{consignId}")
    public ResponseEntity<?> getConsignRequestById(@PathVariable Long storeId, @PathVariable Long consignId) {

    }

    // 회수 신청 메뉴
    @GetMapping("/api/admin/{storeId}/requests/withdrawal")
    public ResponseEntity<?> getAllWithdrawalRequests(@PathVariable Long storeId) {
        List<Consign> consigns = storeAdminService.getConsignRequests(storeId, ConsignStatus.WITHDRAWN);
        List<ConsignResponse> consignResps = consigns.stream().map(ConsignResponse::from).toList();

        return ResponseEntity.ok().body(consignResps);
    }

    // 회수 신청한 항목 1개 클릭시
    @GetMapping("/api/admin/{storeId}/requests/withdrawal/{consignId}")
    public ResponseEntity<?> getWithdrawalRequestById(@PathVariable Long storeId, @PathVariable Long consignId) {

    }

    // 맡기기 수락 시
    @PostMapping("/api/admin/requests")
    public ResponseEntity<?> setConsignStatus(@RequestBody Map<String, String> consignRequest) {
        storeAdminService.setConsignStatus(storeId, ConsignStatus.DECLINED);
        return ResponseEntity.ok().build();
    }

}
