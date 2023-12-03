package com.example.travel.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class User {

    private Long uid;
    private String unique_id;
    private String pw;

    private String email;
    private String nickname;

    private RoleType role_type;
    private ProviderType provider_type;

    private LocalDateTime created_at;
    private LocalDateTime modified_at;
    private Integer travel_count;
    private Integer level;

    @Builder // 소셜 로그인으로 가입하는 경우
    public User(String unique_id, String email, String nickname, RoleType role_type, ProviderType provider_type,
                LocalDateTime created_at, LocalDateTime modified_at) {
        this.unique_id = unique_id;
        this.pw = "";
        this.email = email != null ? email : "NO_EMAIL";
        this.nickname = nickname;
        this.role_type = role_type;
        this.provider_type = provider_type;
        this.created_at = created_at;
        this.modified_at = modified_at;
        this.travel_count = 0;
        this.level = 1;
    }

    @Builder // 자체 로그인으로 가입
    public User(String unique_id, String pw, String email, String nickname, RoleType role_type) {
        this.unique_id = unique_id; // 자체 로그인으로 가입 시 초기에는 Emtpy String => 이메일 인증 후 UUID 발급
        this.pw = pw;
        this.email = email;
        this.nickname = nickname;
        this.role_type = role_type;
        this.provider_type = ProviderType.NO;
        this.created_at = LocalDateTime.now();
        this.modified_at = LocalDateTime.now();
        this.travel_count = 0;
        this.level = 1;
    }

    @Builder // saveRefreshToken의 Target User
    public User (Long uid, String unique_id, String pw, String email, String nickname,
                 RoleType role_type, ProviderType provider_type, int travel_count, int level) {
        this.uid = uid;
        this.unique_id = unique_id;
        this.pw = pw;
        this.email = email;
        this.nickname = nickname;
        this.role_type = role_type;
        this.provider_type = provider_type;
        this.travel_count = travel_count;
        this.level = level;
    }

    @Builder
    public User(String nickname, LocalDateTime modified_at) { // 닉네임을 변경하는 경우
        this.nickname = nickname;
        this.modified_at = modified_at;
    }

    @Builder
    public User(Integer travel_count, Integer level) { // 여행을 완료하여 여행 횟수&레벨을 변경하는 경우
        this.travel_count = travel_count;
        this.level = level;
    }

    @Builder
    public User(Long uid, String email, String nickname) { // 자체 로그인 유저가 이미 가입되어 있는지 확인
        this.uid = uid;
        this.email = email;
        this.nickname = nickname;
    }

    public void changeRole(RoleType role_type) { // 유저 권한 변경
        this.role_type = role_type;
    }
}