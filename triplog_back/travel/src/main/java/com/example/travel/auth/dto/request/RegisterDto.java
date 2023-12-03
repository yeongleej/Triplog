package com.example.travel.auth.dto.request;

import com.example.travel.auth.entity.ProviderType;
import com.example.travel.auth.entity.RoleType;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class RegisterDto implements Serializable {

    private final String unique_id;
    private final String pw;
    private final String email;
    private final String nickname;
    private final RoleType role_type;
    private final ProviderType provider_type;
    private final LocalDateTime created_at;
    private final LocalDateTime modified_at;

    @Builder
    public RegisterDto(String unique_id, String pw, String email, String nickname,
                       RoleType role_type, ProviderType provider_type) {
        this.unique_id = unique_id;
        this.pw = pw;
        this.email = email;
        this.nickname = nickname;
        this.role_type = role_type;
        this.provider_type = provider_type;
        this.created_at = LocalDateTime.now();
        this.modified_at = LocalDateTime.now();
    }
}