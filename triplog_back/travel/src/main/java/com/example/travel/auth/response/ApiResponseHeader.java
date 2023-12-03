package com.example.travel.auth.response;

import lombok.Getter;

@Getter
public class ApiResponseHeader {
    private final int code;
    private final String message;

    public ApiResponseHeader(int code, String message) {
        this.code = code;
        this.message = message;
    }
}