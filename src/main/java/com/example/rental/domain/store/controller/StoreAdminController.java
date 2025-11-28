package com.example.rental.domain.store.controller;

import com.example.rental.common.ApiResponse;
import com.example.rental.domain.consign.dto.ConsignResponse;
import com.example.rental.domain.consign.entity.Consign;
import com.example.rental.domain.consign.entity.ConsignStatus;
import com.example.rental.domain.store.dto.RentalStatusResponse;
import com.example.rental.domain.store.dto.StoreInfoResponse;
import com.example.rental.domain.store.dto.StoreItemResponse;
import com.example.rental.domain.store.service.StoreAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StoreAdminController {

    public final StoreAdminService storeAdminService;
//    1.
//            "매장 정보" 메뉴
//    GET /api/admin/{store_id}
//    req: store_id
//    res: 이름 사진 상태 총수익 대여현황수 보유물품수 맡기기신청건수 회수신청건수
    @GetMapping("/api/admin/{store_id}")
    public ResponseEntity<ApiResponse<StoreInfoResponse>> getStoreInfo(@PathVariable Long store_id){
        StoreInfoResponse response = storeAdminService.getStoreInfo(store_id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }


//2.
//        "대여 현황 확인" 클릭시
//    GET /api/admin/{store_id}/rentals
//    req: store_id
//    res: list(reservation_id, 이름, 총대여비, 상태, 사진)
//
    @GetMapping("/api/admin/{store_id}/rentals")
    public ResponseEntity<ApiResponse<List<RentalStatusResponse>>> getRentedItems(
            @PathVariable Long store_id
    ){
        List<RentalStatusResponse> response = storeAdminService.getRentedItems(store_id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }



//3.
//        "보유 물품" 클릭시
//    GET /api/admin/{store_id}/items
//    req: store_id
//    res: list(item_id, 이름, 시간당, 보증금, 상태, 사진)


    @GetMapping("/api/admin/{store_id}/items")
    public ResponseEntity<ApiResponse<List<StoreItemResponse>>> getStoreitems (
            @PathVariable Long store_id
    ){
        List<StoreItemResponse> response = storeAdminService.getStoreItems(store_id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 맡기기 신청 메뉴
    @GetMapping("/api/admin/{storeId}/requests/consign")
    public ResponseEntity<?> getAllConsignRequests(@PathVariable Long storeId) {
        List<Consign> consigns = storeAdminService.getConsignRequests(storeId, ConsignStatus.WAITING);
        List<ConsignResponse> consignResps = consigns.stream().map(ConsignResponse::from).toList();

        return ResponseEntity.ok().body(consignResps);
    }

    // 맡기기 신청한 항목 1개 클릭시
    @GetMapping("/api/admin/{storeId}/requests/consign/{consignId}")
    public ResponseEntity<ApiResponse<ConsignResponse>> getConsignRequestById(@PathVariable Long storeId, @PathVariable Long consignId) {
        Consign consign = storeAdminService.getConsignById(consignId);
        ConsignResponse response = ConsignResponse.from(consign);
        return ResponseEntity.ok(ApiResponse.success(response));
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
    public ResponseEntity<ApiResponse<ConsignResponse>> getWithdrawalRequestById(@PathVariable Long storeId, @PathVariable Long consignId) {
        Consign consign = storeAdminService.getConsignById(consignId);
        ConsignResponse response = ConsignResponse.from(consign);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 맡기기/회수 수락/거절 시
    @PostMapping("/api/admin/requests")
    public ResponseEntity<ApiResponse<Void>> setConsignStatus(@RequestBody Map<String, Object> consignRequest) {
        Long consignId = Long.parseLong(consignRequest.get("consignId").toString());
        String statusStr = consignRequest.get("status").toString();
        ConsignStatus status = ConsignStatus.valueOf(statusStr);

        storeAdminService.setConsignStatus(consignId, status);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
