package com.example.travel.auth.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChangePwDto {

    private final String curPw;
    private final String newPw;

    @Builder
    public ChangePwDto(String curPw, String newPw) {
        this.curPw = curPw;
        this.newPw = newPw;
    }
}