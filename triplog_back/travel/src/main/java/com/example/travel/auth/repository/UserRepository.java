package com.example.travel.auth.repository;

import com.example.travel.auth.dto.request.RegisterDto;
import com.example.travel.auth.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.SQLException;
import java.time.LocalDateTime;

@Mapper
public interface UserRepository {

    // 직접 가입한 회원에 대한 로그인 처리
    @Select("select uid, unique_id, pw, email, nickname, role_type, provider_type, " +
            "travel_count, level from user where email=#{email}")
    User login(String email) throws SQLException;

    // 직접 가입한 회원 저장
    @Insert("insert into user(unique_id, pw, email, nickname, role_type, provider_type, created_at, modified_at) " +
            "values(#{unique_id}, #{pw}, #{email}, #{nickname}, #{role_type}, #{provider_type},  #{created_at}, #{modified_at})")
    void register(RegisterDto registerDto) throws SQLException;

    // 소셜 로그인으로 가입한 회원 저장
    @Insert("insert into user(unique_id, pw, email, nickname, role_type, provider_type, created_at, modified_at) " +
            "values(#{unique_id}, #{pw}, #{email}, #{nickname}, #{role_type}, #{provider_type}, #{created_at}, #{modified_at})")
    void registerSocial(User user) throws SQLException;

    // 직접 가입한 회원의 이메일 인증 => unique_id 자동 발급 => 업데이트
    @Update("update user set email=#{email}, modified_at=#{modified_at} where unique_id=#{unique_id}")
    String emailVerification(String email, LocalDateTime modified_at, String unique_id) throws SQLException;

    @Select("select pw from user where uid=#{uid}")
    String findUserByUid(Long uid) throws SQLException;

    // 가입여부 조회
    @Select("select uid, unique_id, pw, email, nickname, role_type, provider_type," +
            "travel_count, level from user where unique_id=#{unique_id}")
    User findByUniqueId(String uniqueId) throws SQLException;

    @Select("select email from user where email=#{email}")
    String findByEmail(String email) throws SQLException;

    @Update("update user set pw=#{newPw} where uid=#{uid}")
    int changePw(Long uid, String newPw) throws SQLException;

    @Update("update user set nickname=#{nickname} where uid=#{uid}")
    void modifyNickname(Long uid, String nickname);
}