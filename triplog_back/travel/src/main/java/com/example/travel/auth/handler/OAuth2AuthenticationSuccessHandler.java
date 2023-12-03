package com.example.travel.auth.handler;

import com.example.travel.auth.entity.UserPrincipal;
import com.example.travel.auth.provider.JwtTokenProvider;
import com.example.travel.auth.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;
import java.net.URI;

import static com.example.travel.auth.repository.CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final static int cookieExpireSeconds = 1800;

    @Value("${app.oauth2.authorizedRedirectUri}")
    private String authRedirectUri;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String targetUrl = determineTargetUrl(request, response, authentication);
        if(response.isCommitted()) {
            log.debug("Response has already been committed");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CookieUtil.addCookie(response, "localhost", "uid", user.getUid().toString(), cookieExpireSeconds);
        CookieUtil.addCookie(response, "localhost", "email", user.getEmail(), cookieExpireSeconds);
        CookieUtil.addCookie(response, "localhost", "nickname", user.getNickname(), cookieExpireSeconds);

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);


        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("Unauthorized Redirect URI");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl()) + "api/v1/login/token/";
        String acessToken = jwtTokenProvider.createAcessToken(authentication);

        CookieUtil.addCookie(response,"localhost", "acc-tk", acessToken, cookieExpireSeconds);

        try {
            jwtTokenProvider.createRefreshToken(authentication, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return UriComponentsBuilder.fromUriString(targetUrl).queryParam("acc-tk", acessToken)
                .build().toUriString();
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        URI authorizedUri = URI.create(authRedirectUri);

        return authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                && authorizedUri.getPort() == clientRedirectUri.getPort();
    }
}