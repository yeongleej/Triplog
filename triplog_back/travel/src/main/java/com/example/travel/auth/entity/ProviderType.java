package com.example.travel.auth.entity;

import lombok.Getter;

@Getter
public enum ProviderType {
    NO, // 자체 로그인
    KAKAO,
    NAVER,
    GOOGLE
}