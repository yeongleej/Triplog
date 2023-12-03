package com.example.travel.auth.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ModifyInfoDto {
    public final Long uid;
    public final String nickname;

    @Builder

    public ModifyInfoDto(Long uid, String nickname) {
        this.uid = uid;
        this.nickname = nickname;
    }
}
