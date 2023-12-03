package com.example.travel.auth.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class RegistInfoDto implements Serializable {

    private final String email;
    private final String pw;
    private final String nickname;

    @Builder
    public RegistInfoDto(String email, String pw, String nickname) {
        this.email = email;
        this.pw = pw;
        this.nickname = nickname;
    }
}