package com.example.travel.auth.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class LoginDto implements Serializable {

    private final String email;
    private final String pw;

    @Builder
    public LoginDto(String email, String pw) {
        this.email = email;
        this.pw = pw;
    }
}