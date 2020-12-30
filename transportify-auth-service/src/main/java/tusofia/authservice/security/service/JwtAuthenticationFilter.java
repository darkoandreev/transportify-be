package tusofia.authservice.security.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tusofia.authservice.security.dto.Token;
import tusofia.authservice.security.utils.CookieUtil;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
        LoginResponse loginResponse = new LoginResponse(token.getTokenValue(), true, "Successfully logged in!");
        response.getWriter().write(new Gson().toJson(loginResponse));

    }

    private static class LoginResponse {
        private String token;
        private boolean success;
        private String message;

        public LoginResponse(String token, boolean success, String message) {
            this.token = token;
            this.success = success;
            this.message = message;
        }
    }

    @Getter
    @Setter
    public static class User implements UserDetails {
        private Long id;
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String gender;
        private Date dateOfBirth;
        private boolean isEnabled;

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return this.isEnabled;
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.emptyList();
        }
    }
}
