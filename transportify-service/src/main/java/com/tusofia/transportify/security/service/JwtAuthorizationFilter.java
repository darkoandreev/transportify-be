package com.tusofia.transportify.security.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final ITokenProvider tokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, ITokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        Cookie cookie = WebUtils.getCookie(request, "accessToken");

        if (cookie == null || !StringUtils.hasText(cookie.getValue())) {
            chain.doFilter(request, response);
            return;
        }


        if (this.tokenProvider.validateToken(cookie.getValue())) {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(cookie);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(Cookie cookie) {
        return this.tokenProvider.getAuthentication(cookie.getValue());
    }
}
