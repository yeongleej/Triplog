package com.example.travel.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import com.example.travel.auth.provider.JwtTokenProvider;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider provider) {
        this.jwtTokenProvider = provider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = parseBearerToken(request); // Request에서 Access Token 가져오기

        if(StringUtils.hasText(token) && jwtTokenProvider.isValidToken(token)) { // 유효성 검사 통과 시
            Authentication authentication = jwtTokenProvider.getAuthentication(token); // 유저의 인증 정보 가져오기
            // 현재 인증된 유저의 정보를 저장하는 Principal 객체를 Authentication 객체가 관리 -> Authentication을 SecurityContext가 관리
            // -> SecurityContext를 SecurityContextHolder가 관리
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 유저의 정보를 기반으로 SecurityContextHolder에 Authentication 객체 생성 및 저장
            log.debug("Authentication Information of " + authentication.getName());
        }
        else {
            log.debug("Cannot find valid JWT Token");
        }

        filterChain.doFilter(request, response);
    }

    // Request에서 Authorization Header 값을 받아 Access Token 값만을 추출
    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}