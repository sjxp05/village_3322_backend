package com.example.rental.domain.consign.entity;

public enum ConsignStatus {
    WAITING, // 사용자가 신청 후 대기중
    ACTIVE, // 가게에서 수락, 대여해줄 수 있는 상태
    DECLINED, // 가게에서 거절한 상태
    WITHDRAWN, // 신청했던 사용자가 다시 회수를 원하는 상태
}
