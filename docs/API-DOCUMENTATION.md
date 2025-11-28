# Village 3322 렌탈 서비스 API 문서

> **Version:** 1.0.0
> **Base URL:** `http://localhost:8080`
> **최종 수정일:** 2025-11-28

---

## 목차

1. [공통 사항](#공통-사항)
   - [공통 응답 형식](#공통-응답-형식)
   - [Enum 타입 정의](#enum-타입-정의)
2. [아이템 API](#아이템-api-storecontroller)
3. [위탁 API](#위탁-api-consigncontroller)
4. [예약 API](#예약-api-reservationcontroller)
5. [사용자 API](#사용자-api-usercontroller)
6. [매장 관리자 API](#매장-관리자-api-storeadmincontroller)

---

## 공통 사항

### 공통 응답 형식

대부분의 API는 아래의 공통 응답 형식(`ApiResponse<T>`)을 사용합니다:

```json
{
  "success": true,
  "data": { ... },
  "message": null
}
```

| 필드 | 타입 | 설명 |
|------|------|------|
| `success` | `boolean` | 요청 성공 여부 |
| `data` | `T` | 응답 데이터 (타입은 API마다 다름) |
| `message` | `string \| null` | 에러 발생 시 메시지 |

**에러 응답 예시:**
```json
{
  "success": false,
  "data": null,
  "message": "해당 아이템을 찾을 수 없습니다."
}
```

---

### Enum 타입 정의

#### ItemStatus (아이템 상태)
| 값 | 설명 |
|---|---|
| `AVAILABLE` | 대여 가능 |
| `RENTED` | 대여 중 |

#### StoreStatus (매장 상태)
| 값 | 설명 |
|---|---|
| `SPACE_AVAILABLE` | 공간 여유 있음 |
| `ALMOST_FULL` | 거의 가득 참 |
| `FULL` | 가득 참 |

#### StoreCategory (매장 카테고리)
| 값 | 설명 |
|---|---|
| `CONVENIENCE` | 편의점 |
| `HARDWARE` | 철물점 |

#### ConsignStatus (위탁 상태)
| 값 | 설명 |
|---|---|
| `WAITING` | 사용자가 신청 후 대기 중 |
| `ACTIVE` | 가게에서 수락, 대여 가능 상태 |
| `DECLINED` | 가게에서 거절한 상태 |
| `WITHDRAWN` | 사용자가 회수를 신청한 상태 |

#### ReservationStatus (예약 상태)
| 값 | 설명 |
|---|---|
| `PAID` | 결제 완료 (사용 전) |
| `CANCELED` | 취소됨 |
| `IN_USE` | 사용 중 |
| `RETURNED` | 반납 완료 |
| `LOST` | 분실 |
| `DAMAGED` | 파손 |

#### PaymentType (결제 타입)
| 값 | 설명 |
|---|---|
| `REFUND` | 환불 |
| `ADDITIONAL` | 추가 결제 |
| `NONE` | 차액 없음 |

---

## 아이템 API (StoreController)

### 1. 모든 대여 가능 아이템 목록 조회

근처의 모든 대여 가능한 아이템을 조회합니다.

**Endpoint:** `GET /api/items`

**Request:** 없음

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "storeId": 1,
      "ownerId": 2,
      "name": "캠핑 텐트",
      "description": "4인용 캠핑 텐트입니다.",
      "photoUrl": "https://example.com/tent.jpg",
      "feePerDay": 5000,
      "deposit": 50000,
      "quantity": null,
      "status": "AVAILABLE",
      "isConsignedItem": true
    }
  ],
  "message": null
}
```

**Response 필드 상세:**

| 필드 | 타입 | 설명 |
|------|------|------|
| `id` | `Long` | 아이템 고유 ID |
| `storeId` | `Long` | 보관 매장 ID |
| `ownerId` | `Long \| null` | 소유자 ID (위탁 물품인 경우) |
| `name` | `String` | 아이템 이름 |
| `description` | `String` | 아이템 설명 |
| `photoUrl` | `String` | 아이템 사진 URL |
| `feePerDay` | `Long` | 일일 대여료 (원) |
| `deposit` | `Long` | 보증금 (원) |
| `quantity` | `Integer \| null` | 수량 |
| `status` | `ItemStatus` | 아이템 상태 |
| `isConsignedItem` | `boolean` | 위탁 물품 여부 |

---

### 2. 아이템 상세 정보 조회

특정 아이템의 상세 정보와 해당 매장의 다른 물품 목록을 조회합니다.

**Endpoint:** `GET /api/items/{itemId}`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `itemId` | `Long` | O | 아이템 ID |

**Response:**
```json
{
  "name": "캠핑 텐트",
  "photo_url": "https://example.com/tent.jpg",
  "fee_per_day": 5000,
  "deposit": 50000,
  "store_name": "우리동네 편의점",
  "store_lat": 37.5665,
  "store_lon": 126.9780,
  "store_other_items": [
    {
      "id": 2,
      "storeId": 1,
      "ownerId": null,
      "name": "캠핑 의자",
      "description": "접이식 캠핑 의자",
      "photoUrl": "https://example.com/chair.jpg",
      "feePerDay": 2000,
      "deposit": 20000,
      "quantity": null,
      "status": "AVAILABLE",
      "isConsignedItem": false
    }
  ]
}
```

**Response 필드 상세:**

| 필드 | 타입 | 설명 |
|------|------|------|
| `name` | `String` | 아이템 이름 |
| `photo_url` | `String` | 아이템 사진 URL |
| `fee_per_day` | `Long` | 일일 대여료 (원) |
| `deposit` | `Long` | 보증금 (원) |
| `store_name` | `String` | 매장 이름 |
| `store_lat` | `Double` | 매장 위도 |
| `store_lon` | `Double` | 매장 경도 |
| `store_other_items` | `List<ItemDetailResponse>` | 같은 매장의 다른 물품 목록 |

---

### 3. 지도용 매장 목록 조회

지도에 표시할 모든 매장 위치를 조회합니다.

**Endpoint:** `GET /api/items/map`

**Request:** 없음

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "우리동네 편의점",
      "lat": 37.5665,
      "lon": 126.9780,
      "category": "CONVENIENCE",
      "status": "SPACE_AVAILABLE"
    }
  ],
  "message": null
}
```

**Response 필드 상세:**

| 필드 | 타입 | 설명 |
|------|------|------|
| `id` | `Long` | 매장 고유 ID |
| `name` | `String` | 매장 이름 |
| `lat` | `Double` | 위도 |
| `lon` | `Double` | 경도 |
| `category` | `StoreCategory` | 매장 카테고리 |
| `status` | `StoreStatus` | 매장 상태 |

---

### 4. 특정 매장의 아이템 목록 조회

특정 매장이 보유한 모든 아이템을 조회합니다.

**Endpoint:** `GET /api/items/stores/{storeId}`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `storeId` | `Long` | O | 매장 ID |

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "storeId": 1,
      "ownerId": 2,
      "name": "캠핑 텐트",
      "description": "4인용 캠핑 텐트입니다.",
      "photoUrl": "https://example.com/tent.jpg",
      "feePerDay": 5000,
      "deposit": 50000,
      "quantity": null,
      "status": "AVAILABLE",
      "isConsignedItem": true
    }
  ],
  "message": null
}
```

---

## 위탁 API (ConsignController)

### 1. 위탁 가능한 매장 목록 조회

물건을 맡길 수 있는 매장 목록을 조회합니다 (목록/지도 형식 동일).

**Endpoint:** `GET /api/consign`

**Query Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `lat` | `Double` | O | 현재 위치 위도 |
| `lon` | `Double` | O | 현재 위치 경도 |

**Request 예시:**
```
GET /api/consign?lat=37.5665&lon=126.9780
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "우리동네 편의점",
    "lat": 37.5665,
    "lon": 126.9780,
    "category": "CONVENIENCE",
    "status": "SPACE_AVAILABLE"
  }
]
```

---

### 2. 위탁할 매장 상세 정보 조회

위탁할 매장 선택 시 상세 정보를 표시합니다.

**Endpoint:** `GET /api/consign/stores/{storeId}`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `storeId` | `Long` | O | 매장 ID |

**Response:**
```json
{
  "name": "우리동네 편의점",
  "lat": 37.5665,
  "lon": 126.9780,
  "photo_url": "https://example.com/store.jpg",
  "status": "SPACE_AVAILABLE",
  "description": "24시간 운영하는 편의점입니다."
}
```

**Response 필드 상세:**

| 필드 | 타입 | 설명 |
|------|------|------|
| `name` | `String` | 매장 이름 |
| `lat` | `Double` | 위도 |
| `lon` | `Double` | 경도 |
| `photo_url` | `String` | 매장 사진 URL |
| `status` | `StoreStatus` | 매장 상태 |
| `description` | `String` | 매장 설명 |

---

### 3. 물건 위탁 신청

물건 맡기기를 신청합니다.

**Endpoint:** `POST /api/consign`

**Request Body:**
```json
{
  "owner_id": "1",
  "store_id": "1",
  "name": "캠핑 텐트",
  "description": "4인용 캠핑 텐트입니다. 상태 양호합니다.",
  "photo_url": "https://example.com/my-tent.jpg",
  "fee_per_day": "5000",
  "deposit": "50000"
}
```

**Request 필드 상세:**

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| `owner_id` | `String` | O | 물건 소유자(사용자) ID |
| `store_id` | `String` | O | 위탁할 매장 ID |
| `name` | `String` | O | 물건 이름 |
| `description` | `String` | O | 물건 설명 |
| `photo_url` | `String` | O | 물건 사진 URL |
| `fee_per_day` | `String` | O | 희망 일일 대여료 (원) |
| `deposit` | `String` | O | 희망 보증금 (원) |

**Response:**
```json
{
  "id": 1,
  "store": { ... },
  "owner": { ... },
  "item": { ... },
  "totalProfit": 0,
  "status": "WAITING",
  "createdAt": "2025-11-28T10:00:00",
  "updatedAt": "2025-11-28T10:00:00"
}
```

> **Note:** 이 응답은 Consign Entity 전체를 반환합니다. 필요에 따라 프론트엔드에서 필요한 필드만 사용하세요.

---

## 예약 API (ReservationController)

### 1. 사용자의 모든 예약 내역 조회

특정 사용자의 모든 예약 내역을 조회합니다.
> **Note:** 상태가 `PAID`와 `IN_USE`인 예약은 QR 코드도 포함됩니다.

**Endpoint:** `GET /api/reservations/{userId}`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `userId` | `Long` | O | 사용자 ID |

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "userId": 1,
      "itemId": 1,
      "itemName": "캠핑 텐트",
      "initialPaidFee": 55000,
      "qrToken": "550e8400-e29b-41d4-a716-446655440000",
      "status": "PAID",
      "createdAt": "2025-11-28T10:00:00"
    }
  ],
  "message": null
}
```

**Response 필드 상세 (ReservationResponse):**

| 필드 | 타입 | 설명 |
|------|------|------|
| `id` | `Long` | 예약 고유 ID |
| `userId` | `Long` | 사용자 ID |
| `itemId` | `Long` | 아이템 ID |
| `itemName` | `String` | 아이템 이름 |
| `initialPaidFee` | `Long` | 초기 결제 금액 (대여료 + 보증금) |
| `qrToken` | `String` | QR 코드용 토큰 (UUID) |
| `status` | `ReservationStatus` | 예약 상태 |
| `createdAt` | `LocalDateTime` | 예약 생성 시간 |

---

### 2. 반납용 QR 코드 조회

특정 예약의 반납을 위한 개별 QR 정보를 조회합니다.

**Endpoint:** `GET /api/reservations/{userId}/{reservationId}`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `userId` | `Long` | O | 사용자 ID |
| `reservationId` | `Long` | O | 예약 ID |

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "userId": 1,
    "itemId": 1,
    "itemName": "캠핑 텐트",
    "initialPaidFee": 55000,
    "qrToken": "550e8400-e29b-41d4-a716-446655440000",
    "status": "IN_USE",
    "createdAt": "2025-11-28T10:00:00"
  },
  "message": null
}
```

---

### 3. 예약 생성 (결제)

아이템을 예약하고 결제합니다.

**Endpoint:** `POST /api/reservations/payment`

**Request Body:**
```json
{
  "userId": 1,
  "itemId": 1,
  "usageDays": 3
}
```

**Request 필드 상세 (ReservationCreateRequest):**

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| `userId` | `Long` | O | 사용자 ID |
| `itemId` | `Long` | O | 대여할 아이템 ID |
| `usageDays` | `Long` | O | 대여 일수 |

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "userId": 1,
    "itemId": 1,
    "itemName": "캠핑 텐트",
    "initialPaidFee": 65000,
    "qrToken": "550e8400-e29b-41d4-a716-446655440000",
    "status": "PAID",
    "createdAt": "2025-11-28T10:00:00"
  },
  "message": null
}
```

> **결제 금액 계산:** `initialPaidFee = (feePerDay × usageDays) + deposit`

---

### 4. 사용 시작

QR 코드를 클릭하여 물품 사용을 시작합니다.

**Endpoint:** `POST /api/reservations/{reservationId}/start`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `reservationId` | `Long` | O | 예약 ID |

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "userId": 1,
    "itemId": 1,
    "itemName": "캠핑 텐트",
    "initialPaidFee": 65000,
    "qrToken": "550e8400-e29b-41d4-a716-446655440000",
    "status": "IN_USE",
    "createdAt": "2025-11-28T10:00:00"
  },
  "message": null
}
```

> **상태 변화:** `PAID` → `IN_USE`

---

### 5. 대여 연장

사용 중인 물품의 대여 기간을 연장합니다.

**Endpoint:** `PATCH /api/reservations/{reservationId}/extend`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `reservationId` | `Long` | O | 예약 ID |

**Query Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `additionalDays` | `Long` | O | 추가 대여 일수 |

**Request 예시:**
```
PATCH /api/reservations/1/extend?additionalDays=2
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "userId": 1,
    "itemId": 1,
    "itemName": "캠핑 텐트",
    "initialPaidFee": 65000,
    "qrToken": "550e8400-e29b-41d4-a716-446655440000",
    "status": "IN_USE",
    "createdAt": "2025-11-28T10:00:00"
  },
  "message": null
}
```

> **Note:** 상태가 `IN_USE`일 때만 연장 가능합니다.

---

### 6. 반납하기

QR 코드를 클릭하여 물품을 반납합니다.

**Endpoint:** `POST /api/reservations/{reservationId}/return`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `reservationId` | `Long` | O | 예약 ID |

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "userId": 1,
    "itemId": 1,
    "itemName": "캠핑 텐트",
    "initialPaidFee": 65000,
    "qrToken": "550e8400-e29b-41d4-a716-446655440000",
    "status": "RETURNED",
    "createdAt": "2025-11-28T10:00:00"
  },
  "message": null
}
```

> **상태 변화:** `IN_USE` → `RETURNED`

---

### 7. 최종 정산

정산 버튼을 눌러 최종 결제/환불을 진행합니다.

**Endpoint:** `POST /api/reservations/{reservationId}/finalize`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `reservationId` | `Long` | O | 예약 ID |

**Response:**
```json
{
  "success": true,
  "data": {
    "reservationId": 1,
    "userId": 1,
    "itemId": 1,
    "itemName": "캠핑 텐트",
    "status": "RETURNED",
    "initialPaidFee": 65000,
    "actualPaidFee": 60000,
    "difference": 5000,
    "paymentType": "REFUND",
    "message": "환불 금액: 5000원",
    "usageDays": 3,
    "actualUsageDays": 2,
    "startedAt": "2025-11-28T10:00:00",
    "endedAt": "2025-11-30T10:00:00"
  },
  "message": null
}
```

**Response 필드 상세 (PaymentFinalizeResponse):**

| 필드 | 타입 | 설명 |
|------|------|------|
| `reservationId` | `Long` | 예약 ID |
| `userId` | `Long` | 사용자 ID |
| `itemId` | `Long` | 아이템 ID |
| `itemName` | `String` | 아이템 이름 |
| `status` | `ReservationStatus` | 예약 상태 |
| `initialPaidFee` | `Long` | 초기 결제 금액 |
| `actualPaidFee` | `Long` | 실제 결제 금액 |
| `difference` | `Long` | 차액 (양수: 환불, 음수: 추가 결제) |
| `paymentType` | `PaymentType` | 결제 타입 (`REFUND`, `ADDITIONAL`, `NONE`) |
| `message` | `String` | 안내 메시지 |
| `usageDays` | `Long` | 예약한 사용 일수 |
| `actualUsageDays` | `Long` | 실제 사용 일수 |
| `startedAt` | `LocalDateTime` | 사용 시작 시간 |
| `endedAt` | `LocalDateTime` | 사용 종료 시간 |

---

## 사용자 API (UserController)

### 1. 사용자 설정 정보 조회

사용자의 설정 화면에 필요한 정보를 조회합니다.

**Endpoint:** `GET /api/users/api/users/{userId}/settings`

> **Note:** URL에 `/api/users`가 중복되어 있습니다. (`@RequestMapping` + `@GetMapping`)

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `userId` | `Long` | O | 사용자 ID |

**Response:**
```json
{
  "nickname": "홍길동",
  "point": 10000,
  "consign_history": {
    "우리동네 편의점": [1, "캠핑 텐트", 5000],
    "동네 철물점": [2, "전동 드릴", 3000]
  }
}
```

**Response 필드 상세:**

| 필드 | 타입 | 설명 |
|------|------|------|
| `nickname` | `String` | 사용자 닉네임 |
| `point` | `Long` | 보유 포인트 |
| `consign_history` | `Map<String, List<?>>` | 위탁 내역 (매장명 → [아이템ID, 아이템명, 일일대여료]) |

---

## 매장 관리자 API (StoreAdminController)

### 1. 매장 정보 조회

매장의 기본 정보와 통계를 조회합니다.

**Endpoint:** `GET /api/admin/{store_id}`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `store_id` | `Long` | O | 매장 ID |

**Response:**
```json
{
  "success": true,
  "data": {
    "storeId": 1,
    "name": "우리동네 편의점",
    "photoUrl": "https://example.com/store.jpg",
    "status": "SPACE_AVAILABLE",
    "totalRevenue": 150000,
    "rentalCount": 5,
    "itemCount": 10,
    "consignRequestCount": 2,
    "withdrawalRequestCount": 1
  },
  "message": null
}
```

**Response 필드 상세 (StoreInfoResponse):**

| 필드 | 타입 | 설명 |
|------|------|------|
| `storeId` | `Long` | 매장 ID |
| `name` | `String` | 매장 이름 |
| `photoUrl` | `String` | 매장 사진 URL |
| `status` | `StoreStatus` | 매장 상태 |
| `totalRevenue` | `Long` | 총 수익 (원) |
| `rentalCount` | `Long` | 현재 대여 현황 수 |
| `itemCount` | `Long` | 보유 물품 수 |
| `consignRequestCount` | `Long` | 맡기기 신청 건수 |
| `withdrawalRequestCount` | `Long` | 회수 신청 건수 |

---

### 2. 대여 현황 조회

매장의 현재 대여 중인 물품 현황을 조회합니다.

**Endpoint:** `GET /api/admin/{store_id}/rentals`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `store_id` | `Long` | O | 매장 ID |

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "reservationId": 1,
      "itemName": "캠핑 텐트",
      "totalFee": 65000,
      "status": "IN_USE",
      "photoUrl": "https://example.com/tent.jpg"
    }
  ],
  "message": null
}
```

**Response 필드 상세 (RentalStatusResponse):**

| 필드 | 타입 | 설명 |
|------|------|------|
| `reservationId` | `Long` | 예약 ID |
| `itemName` | `String` | 아이템 이름 |
| `totalFee` | `Long` | 총 대여료 |
| `status` | `ReservationStatus` | 예약 상태 |
| `photoUrl` | `String` | 아이템 사진 URL |

---

### 3. 보유 물품 조회

매장이 보유한 모든 물품을 조회합니다.

**Endpoint:** `GET /api/admin/{store_id}/items`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `store_id` | `Long` | O | 매장 ID |

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "itemId": 1,
      "name": "캠핑 텐트",
      "feePerDay": 5000,
      "deposit": 50000,
      "status": "AVAILABLE",
      "photoUrl": "https://example.com/tent.jpg"
    }
  ],
  "message": null
}
```

**Response 필드 상세 (StoreItemResponse):**

| 필드 | 타입 | 설명 |
|------|------|------|
| `itemId` | `Long` | 아이템 ID |
| `name` | `String` | 아이템 이름 |
| `feePerDay` | `Long` | 일일 대여료 (원) |
| `deposit` | `Long` | 보증금 (원) |
| `status` | `ItemStatus` | 아이템 상태 |
| `photoUrl` | `String` | 아이템 사진 URL |

---

### 4. 맡기기 신청 목록 조회

대기 중인 맡기기 신청 목록을 조회합니다.

**Endpoint:** `GET /api/admin/{storeId}/requests/consign`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `storeId` | `Long` | O | 매장 ID |

**Response:**
```json
[
  {
    "consignId": 1,
    "ownerNickname": "홍길동",
    "photoUrl": "https://example.com/item.jpg"
  }
]
```

**Response 필드 상세 (ConsignResponse):**

| 필드 | 타입 | 설명 |
|------|------|------|
| `consignId` | `Long` | 위탁 ID |
| `ownerNickname` | `String` | 소유자 닉네임 |
| `photoUrl` | `String` | 물품 사진 URL |

---

### 5. 맡기기 신청 상세 조회

특정 맡기기 신청의 상세 정보를 조회합니다.

**Endpoint:** `GET /api/admin/{storeId}/requests/consign/{consignId}`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `storeId` | `Long` | O | 매장 ID |
| `consignId` | `Long` | O | 위탁 ID |

**Response:**
```json
{
  "success": true,
  "data": {
    "consignId": 1,
    "ownerNickname": "홍길동",
    "photoUrl": "https://example.com/item.jpg"
  },
  "message": null
}
```

---

### 6. 회수 신청 목록 조회

회수 신청 목록을 조회합니다.

**Endpoint:** `GET /api/admin/{storeId}/requests/withdrawal`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `storeId` | `Long` | O | 매장 ID |

**Response:**
```json
[
  {
    "consignId": 1,
    "ownerNickname": "홍길동",
    "photoUrl": "https://example.com/item.jpg"
  }
]
```

---

### 7. 회수 신청 상세 조회

특정 회수 신청의 상세 정보를 조회합니다.

**Endpoint:** `GET /api/admin/{storeId}/requests/withdrawal/{consignId}`

**Path Parameters:**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| `storeId` | `Long` | O | 매장 ID |
| `consignId` | `Long` | O | 위탁 ID |

**Response:**
```json
{
  "success": true,
  "data": {
    "consignId": 1,
    "ownerNickname": "홍길동",
    "photoUrl": "https://example.com/item.jpg"
  },
  "message": null
}
```

---

### 8. 맡기기/회수 요청 상태 변경

맡기기 또는 회수 요청을 수락/거절합니다.

**Endpoint:** `POST /api/admin/requests`

**Request Body:**
```json
{
  "consignId": 1,
  "status": "ACTIVE"
}
```

**Request 필드 상세:**

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| `consignId` | `Long (String)` | O | 위탁 ID |
| `status` | `String` | O | 변경할 상태 (`ACTIVE`, `DECLINED`, `WITHDRAWN`) |

**상태 변경 시나리오:**

| 현재 상태 | 액션 | 변경 상태 |
|-----------|------|-----------|
| `WAITING` | 수락 | `ACTIVE` |
| `WAITING` | 거절 | `DECLINED` |
| `WITHDRAWN` | 회수 수락 | `DECLINED` 또는 해당 위탁 삭제 |

**Response:**
```json
{
  "success": true,
  "data": null,
  "message": null
}
```

---

## API 엔드포인트 요약

| 구분 | Method | Endpoint | 설명 |
|------|--------|----------|------|
| **아이템** | GET | `/api/items` | 모든 대여 가능 아이템 조회 |
| | GET | `/api/items/{itemId}` | 아이템 상세 정보 |
| | GET | `/api/items/map` | 지도용 매장 목록 |
| | GET | `/api/items/stores/{storeId}` | 특정 매장의 아이템 목록 |
| **위탁** | GET | `/api/consign` | 위탁 가능한 매장 목록 |
| | GET | `/api/consign/stores/{storeId}` | 위탁할 매장 상세 정보 |
| | POST | `/api/consign` | 물건 위탁 신청 |
| **예약** | GET | `/api/reservations/{userId}` | 사용자의 모든 예약 내역 |
| | GET | `/api/reservations/{userId}/{reservationId}` | 반납용 QR 코드 조회 |
| | POST | `/api/reservations/payment` | 예약 생성 (결제) |
| | POST | `/api/reservations/{reservationId}/start` | 사용 시작 |
| | PATCH | `/api/reservations/{reservationId}/extend` | 대여 연장 |
| | POST | `/api/reservations/{reservationId}/return` | 반납하기 |
| | POST | `/api/reservations/{reservationId}/finalize` | 최종 정산 |
| **사용자** | GET | `/api/users/api/users/{userId}/settings` | 사용자 설정 정보 |
| **관리자** | GET | `/api/admin/{store_id}` | 매장 정보 조회 |
| | GET | `/api/admin/{store_id}/rentals` | 대여 현황 조회 |
| | GET | `/api/admin/{store_id}/items` | 보유 물품 조회 |
| | GET | `/api/admin/{storeId}/requests/consign` | 맡기기 신청 목록 |
| | GET | `/api/admin/{storeId}/requests/consign/{consignId}` | 맡기기 신청 상세 |
| | GET | `/api/admin/{storeId}/requests/withdrawal` | 회수 신청 목록 |
| | GET | `/api/admin/{storeId}/requests/withdrawal/{consignId}` | 회수 신청 상세 |
| | POST | `/api/admin/requests` | 요청 상태 변경 |

---

## 주의사항

1. **인증/인가**: 현재 API에는 인증/인가 로직이 구현되어 있지 않습니다. 실제 운영 시 보안 강화가 필요합니다.

2. **URL 중복 문제**: `UserController`의 설정 API 경로가 `/api/users/api/users/{userId}/settings`로 중복되어 있습니다. 백엔드 수정이 필요할 수 있습니다.

3. **응답 형식 일관성**: 일부 API는 `ApiResponse<T>` 래퍼를 사용하고, 일부는 직접 데이터를 반환합니다. 프론트엔드에서 응답 처리 시 주의가 필요합니다.

4. **날짜/시간 형식**: `LocalDateTime` 타입은 ISO 8601 형식(`2025-11-28T10:00:00`)으로 전송됩니다.

5. **숫자 타입**: 금액 관련 필드는 모두 `Long` 타입입니다. JavaScript에서 큰 숫자 처리 시 주의가 필요합니다.

---

*이 문서는 소스 코드 분석을 통해 자동 생성되었습니다.*
