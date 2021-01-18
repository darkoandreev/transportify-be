package com.tusofia.transportify.security.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tusofia.transportify.security.dto.Token;
import com.tusofia.transportify.security.utils.CookieUtil;
import com.tusofia.transportify.user.entity.User;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final ITokenProvider tokenProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ITokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = new ObjectMapper()
                    .readValue(request.getInputStream(), User.class);

            return this.authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getUsername(),
                                    user.getPassword(),
                                    new ArrayList<>()
                            )
                    );
        } catch (IOException e) {
            log.error("User readValue method failed {}", e.getMessage());
        }

        return null;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        LoginResponse loginResponse = new LoginResponse(null, false, failed.getMessage());

        response.getWriter().write(new Gson().toJson(loginResponse));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        User user = (User) authResult.getPrincipal();
        ObjectMapper objectMapper = new ObjectMapper();
        Token token = this.tokenProvider.generateAccessToken(objectMapper.writeValueAsString(user));

        Cookie cookie = CookieUtil.createAccessTokenCookie(token.getTokenValue(), token.getDuration());
        response.addCookie(cookie);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        LoginResponse loginResponse = LoginResponse.builder()
                .token(token.getTokenValue())
                .success(true)
                .message("Successfully logged in!")
                .build();
        response.getWriter().write(new Gson().toJson(loginResponse));

    }

    @Builder
    private static class LoginResponse {
        private final String token;
        private final boolean success;
        private final String message;
    }
}
