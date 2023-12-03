package com.example.travel.auth.config;

import com.example.travel.auth.filter.JwtAuthenticationFilter;
import com.example.travel.auth.handler.OAuth2AuthenticationFailureHandler;
import com.example.travel.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.example.travel.auth.properties.CorsProperties;
import com.example.travel.auth.provider.JwtTokenProvider;
import com.example.travel.auth.repository.CookieAuthorizationRequestRepository;
import com.example.travel.auth.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;
    private final JwtTokenProvider tokenProvider;
    private final CorsProperties properties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 인증에 JWT 토큰을 사용하므로 Session을 생성하지 않음
                .authorizeHttpRequests((authz) -> authz
                        // CORS Preflight Request에 대해서는 spring security를 적용하지 않음
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers("http://192.168.130.81/**").permitAll()
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/login/oauth2/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/route").hasRole("USER")
                        .anyRequest().authenticated())
                .oauth2Login()
                .authorizationEndpoint().baseUri("/login/oauth2").and()
                .redirectionEndpoint().baseUri("/*/oauth2/code/*").and()
                .userInfoEndpoint()
                .userService(oAuth2UserService).and()
                .successHandler(successHandler())
                .failureHandler(failureHandler()).and()
                // JWT 인증 필터 등록
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout().logoutSuccessUrl("/");

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter authenticationFilter() {
        return new JwtAuthenticationFilter(tokenProvider);
    }

    // Cookie 기반 인가 repository -> 인가 응답을 토큰과 연계 및 응답 검증 시 사용
    @Bean
    public CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new CookieAuthorizationRequestRepository();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private AuthenticationSuccessHandler successHandler() {
        return new OAuth2AuthenticationSuccessHandler(tokenProvider);
    }

    private OAuth2AuthenticationFailureHandler failureHandler() {
        return new OAuth2AuthenticationFailureHandler(cookieAuthorizationRequestRepository());
    }

    @Bean // CORS 설정
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setMaxAge(corsConfiguration.getMaxAge());
        corsConfiguration.addAllowedOriginPattern("http://localhost:5173");
        corsConfiguration.addAllowedOriginPattern("http://192.168.130.81:5173");
        corsConfiguration.addAllowedOriginPattern("http://localhost:8080");
        corsConfiguration.addAllowedOriginPattern("http://192.168.130.81:8080");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addExposedHeader("Authorization");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return corsConfigurationSource;
    }
}