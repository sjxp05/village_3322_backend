-- 더미 데이터 삽입 (시립대 주변)
-- 시립대 좌표: 37.5838, 127.0594

-- Users 더미 데이터 (시립대 주변 거주자)
INSERT INTO users (nickname, email, point, latitude, longitude, user_property, created_at, updated_at) VALUES
('김회기', 'hoegi.kim@example.com', 50000, 37.5894, 127.0568, 0, NOW(), NOW()),
('이청량', 'cheongyang.lee@example.com', 30000, 37.5804, 127.0474, 0, NOW(), NOW()),
('박답십', 'dapsim.park@example.com', 45000, 37.5658, 127.0575, 0, NOW(), NOW()),
('최제기', 'jegi.choi@example.com', 25000, 37.5750, 127.0374, 0, NOW(), NOW()),
('정외대', 'oidae.jung@example.com', 60000, 37.5920, 127.0590, 0, NOW(), NOW()),
('송정릉', 'jeongneung.song@example.com', 35000, 37.6050, 127.0500, 0, NOW(), NOW()),
('한길음', 'gireum.han@example.com', 40000, 37.5700, 127.0250, 0, NOW(), NOW()),
('오전농', 'jeonnong.oh@example.com', 55000, 37.5740, 127.0740, 0, NOW(), NOW());

-- Stores 더미 데이터 (시립대 주변 상점들)
INSERT INTO stores (name, latitude, longitude, address, description, photo_url, store_property, status, created_at, updated_at) VALUES
('회기역 공유샵', 37.5900, 127.0560, '서울특별시 동대문구 회기로 117', '회기역 근처 편리한 물품 대여샵입니다.', 'https://example.com/store1.jpg', 150000, 'SPACE_AVAILABLE', NOW(), NOW()),
('청량리 렌탈존', 37.5810, 127.0480, '서울특별시 동대문구 왕산로 1', '청량리역 바로 앞 다양한 물품을 빌려드립니다.', 'https://example.com/store2.jpg', 200000, 'SPACE_AVAILABLE', NOW(), NOW()),
('답십리 나눔터', 37.5665, 127.0580, '서울특별시 동대문구 답십리로 171', '답십리역 5분거리, 친환경 공유 서비스', 'https://example.com/store3.jpg', 120000, 'SPACE_AVAILABLE', NOW(), NOW()),
('제기동 쉐어샵', 37.5755, 127.0380, '서울특별시 동대문구 제기동 1234', '제기동역 인근 맡기기 전문점', 'https://example.com/store4.jpg', 180000, 'FULL', NOW(), NOW()),
('전농동 공유마켓', 37.5745, 127.0745, '서울특별시 동대문구 전농로 51', '전농동 주민들의 사랑방', 'https://example.com/store5.jpg', 95000, 'SPACE_AVAILABLE', NOW(), NOW());

-- Items 더미 데이터 (각 상점별 물품들)
-- 회기역 공유샵 물품들
INSERT INTO items (store_id, owner_id, name, description, photo_url, fee_per_day, deposit, status, created_at, updated_at) VALUES
(1, NULL, '캠핑 텐트 (4인용)', '가족 단위 캠핑에 적합한 4인용 텐트입니다.', 'https://example.com/tent.jpg', 5000, 50000, 'AVAILABLE', NOW(), NOW()),
(1, NULL, '전기 자전거', '시속 25km 전기 자전거, 1회 충전 50km', 'https://example.com/ebike.jpg', 3000, 100000, 'AVAILABLE', NOW(), NOW()),
(1, 1, '빔 프로젝터', '홈시어터용 풀HD 프로젝터', 'https://example.com/projector.jpg', 4000, 80000, 'RENTED', NOW(), NOW()),
(1, NULL, '파티용 스피커', 'JBL 대형 블루투스 스피커', 'https://example.com/speaker.jpg', 2000, 30000, 'AVAILABLE', NOW(), NOW()),

-- 청량리 렌탈존 물품들
(2, NULL, '게임기 (PS5)', '최신 플레이스테이션5 콘솔+컨트롤러2개', 'https://example.com/ps5.jpg', 6000, 150000, 'AVAILABLE', NOW(), NOW()),
(2, 2, '드론 (DJI Mini)', '초경량 접이식 드론', 'https://example.com/drone.jpg', 8000, 200000, 'AVAILABLE', NOW(), NOW()),
(2, NULL, '캠핑용 화로', '접이식 바베큐 그릴 세트', 'https://example.com/grill.jpg', 3000, 40000, 'RENTED', NOW(), NOW()),
(2, NULL, '전동 킥보드', '샤오미 전동킥보드 Pro2', 'https://example.com/kickboard.jpg', 2500, 80000, 'AVAILABLE', NOW(), NOW()),

-- 답십리 나눔터 물품들
(3, NULL, '보드게임 세트', '20종 이상의 보드게임 컬렉션', 'https://example.com/boardgame.jpg', 1500, 20000, 'AVAILABLE', NOW(), NOW()),
(3, 3, '카메라 (미러리스)', '소니 A7 III + 렌즈 3개', 'https://example.com/camera.jpg', 10000, 300000, 'AVAILABLE', NOW(), NOW()),
(3, NULL, '캐리어 (대형)', '여행용 28인치 하드케이스', 'https://example.com/carrier.jpg', 1000, 15000, 'AVAILABLE', NOW(), NOW()),

-- 제기동 쉐어샵 물품들
(4, NULL, '의자 (20개)', '야외 행사용 접이식 의자', 'https://example.com/chairs.jpg', 500, 10000, 'AVAILABLE', NOW(), NOW()),
(4, 4, '무선 청소기', 'LG 코드제로 A9', 'https://example.com/cleaner.jpg', 2000, 50000, 'RENTED', NOW(), NOW()),
(4, NULL, '파라솔 (대형)', '카페용 대형 파라솔', 'https://example.com/parasol.jpg', 3000, 40000, 'AVAILABLE', NOW(), NOW()),

-- 전농동 공유마켓 물품들
(5, NULL, '등산 배낭', '60L 전문가용 등산배낭', 'https://example.com/backpack.jpg', 2000, 30000, 'AVAILABLE', NOW(), NOW()),
(5, 5, '전동 공구 세트', '드릴, 그라인더 등 10종', 'https://example.com/tools.jpg', 5000, 100000, 'AVAILABLE', NOW(), NOW()),
(5, NULL, '파티 의상 (10벌)', '할로윈, 코스프레 의상 세트', 'https://example.com/costume.jpg', 3000, 50000, 'AVAILABLE', NOW(), NOW());

-- Reservations 더미 데이터
INSERT INTO reservations (user_id, item_id, usage_days, quantity, initial_paid_fee, actual_paid_fee, qr_token, image_url, status, created_at, started_at, ended_at, updated_at) VALUES
-- 진행 중인 예약들 (PAID)
(1, 1, 2, 1, 10000, 0, 'QR-2025-001-ABCD1234', 'https://example.com/rental1.jpg', 'PAID', NOW() - INTERVAL '2 days', NULL, NULL, NOW()),
(2, 5, 1, 1, 6000, 0, 'QR-2025-002-EFGH5678', 'https://example.com/rental2.jpg', 'PAID', NOW() - INTERVAL '1 day', NULL, NULL, NOW()),
(3, 9, 1, 1, 1500, 0, 'QR-2025-003-IJKL9012', NULL, 'PAID', NOW() - INTERVAL '3 hours', NULL, NULL, NOW()),

-- 사용 중인 예약들 (IN_USE)
(4, 2, 1, 1, 3000, 0, 'QR-2025-004-MNOP3456', 'https://example.com/rental4.jpg', 'IN_USE', NOW() - INTERVAL '5 hours', NOW() - INTERVAL '4 hours', NULL, NOW()),
(5, 10, 2, 1, 2000, 0, 'QR-2025-005-QRST7890', NULL, 'IN_USE', NOW() - INTERVAL '2 hours', NOW() - INTERVAL '1 hour', NULL, NOW()),

-- 완료된 예약들 (RETURNED)
(6, 4, 1, 1, 2000, 2000, 'QR-2025-006-UVWX1234', 'https://example.com/rental6.jpg', 'RETURNED', NOW() - INTERVAL '7 days', NOW() - INTERVAL '6 days', NOW() - INTERVAL '6 days' + INTERVAL '3 hours', NOW()),
(7, 8, 2, 1, 5000, 5000, 'QR-2025-007-YZAB5678', NULL, 'RETURNED', NOW() - INTERVAL '10 days', NOW() - INTERVAL '9 days', NOW() - INTERVAL '9 days' + INTERVAL '12 hours', NOW()),
(8, 11, 3, 1, 6000, 9000, 'QR-2025-008-CDEF9012', 'https://example.com/rental8.jpg', 'RETURNED', NOW() - INTERVAL '5 days', NOW() - INTERVAL '4 days', NOW() - INTERVAL '3 days', NOW()),

-- 취소된 예약 (CANCELED)
(1, 13, 1, 1, 500, 0, 'QR-2025-009-GHIJ3456', NULL, 'CANCELED', NOW() - INTERVAL '8 days', NULL, NULL, NOW());

-- Consigns 더미 데이터 (맡기기/회수 신청)
INSERT INTO consigns (store_id, owner_id, item_id, total_profit, status, created_at, updated_at) VALUES
-- 대기 중인 맡기기 신청 (WAITING)
(1, 6, 3, 0, 'WAITING', NOW() - INTERVAL '1 day', NOW()),
(3, 7, 10, 0, 'WAITING', NOW() - INTERVAL '2 hours', NOW()),

-- 승인된 맡기기 (APPROVED)
(1, 1, 3, 48000, 'ACTIVE', NOW() - INTERVAL '30 days', NOW() - INTERVAL '29 days'),
(2, 2, 6, 72000, 'ACTIVE', NOW() - INTERVAL '25 days', NOW() - INTERVAL '24 days'),
(3, 3, 10, 35000, 'ACTIVE', NOW() - INTERVAL '20 days', NOW() - INTERVAL '19 days'),
(4, 4, 14, 28000, 'ACTIVE', NOW() - INTERVAL '15 days', NOW() - INTERVAL '14 days'),
(5, 5, 17, 55000, 'ACTIVE', NOW() - INTERVAL '10 days', NOW() - INTERVAL '9 days'),

-- 회수 요청 (WITHDRAWN)
(2, 2, 6, 72000, 'WITHDRAWN', NOW() - INTERVAL '25 days', NOW() - INTERVAL '1 day'),
(4, 4, 14, 28000, 'WITHDRAWN', NOW() - INTERVAL '15 days', NOW() - INTERVAL '2 hours'),

-- 거절된 맡기기 (DECLINED)
(5, 8, 17, 0, 'DECLINED', NOW() - INTERVAL '12 days', NOW() - INTERVAL '11 days');
