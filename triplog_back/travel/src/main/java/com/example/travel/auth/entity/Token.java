package com.example.travel.auth.entity;

import lombok.Builder;
import lombok.Getter;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "token", timeToLive = 604800)
public class Token {

    @Id // Redis Hash의 Key
    private String email;

    @Indexed // Redis Hash의 Field => refresh_token에 할당되어 들어오는 값이 value로 저장
    private String refreshToken;

    @TimeToLive
    private Long expiration;

    @Builder
    public Token(String email, String refreshToken) {
        this.email = email;
        this.refreshToken = refreshToken;
    }
}