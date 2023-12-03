package com.example.travel.auth.controller;

import com.example.travel.auth.dto.request.ChangePwDto;
import com.example.travel.auth.dto.request.LoginDto;
import com.example.travel.auth.dto.request.RegistInfoDto;
import com.example.travel.auth.dto.response.UserRespDto;
import com.example.travel.auth.entity.UserPrincipal;
import com.example.travel.auth.service.CustomOAuth2UserService;
import com.example.travel.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final CustomOAuth2UserService service;
    private final UserService userService;

    public AuthController(CustomOAuth2UserService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDto loginInfo) {
        UserRespDto loginUser;
        try {
            loginUser = userService.login(loginInfo.getEmail(), loginInfo.getPw());
        } catch (Exception e) {
            log.info("Login Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("Login: " + loginInfo.getEmail());
        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> register(@RequestBody RegistInfoDto registerInfo) {
        try {
            userService.register(registerInfo.getEmail(), registerInfo.getPw(), registerInfo.getNickname());
        } catch (SQLException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("Register: " + registerInfo.getEmail() + " " + registerInfo.getNickname());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/modify/{uid}")
    public ResponseEntity<?> changePassword(@PathVariable Long uid, @RequestBody String nickname) {
        try {
            userService.modifyNickname(uid, nickname);
        } catch (DataAccessException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("Modify nickname: " + nickname);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Refresh Token 만료 시 재발급
    @GetMapping("/rtk")
    public ResponseEntity<?> NewAccessToken(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        String rtk;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try {
            rtk = userService.newRefreshToken(authentication);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info(userPrincipal.getEmail() + " " + rtk);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Password 변경
    @PutMapping("/{uid}")
    public ResponseEntity<?> changePassword(@PathVariable Long uid, @RequestBody ChangePwDto passwords) {
        try {
            userService.changePassword(uid, passwords.getCurPw(), passwords.getNewPw());
        } catch (SQLException e) {
            log.info("Password Change Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("Password Changed for User " + uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.logout();

        log.info("Logout: " + authentication.getPrincipal().toString());
    }
}