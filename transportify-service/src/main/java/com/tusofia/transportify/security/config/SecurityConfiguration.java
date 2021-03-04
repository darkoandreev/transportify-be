package com.tusofia.transportify.security.config;

import com.tusofia.transportify.security.service.CustomUserDetailsService;
import com.tusofia.transportify.security.service.ITokenProvider;
import com.tusofia.transportify.security.service.JwtAuthenticationFilter;
import com.tusofia.transportify.security.service.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ITokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf()
            .disable()
            .formLogin().disable()
            .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/api/user/signup", "/api/user/confirm-account", "/transport-chat-socket/*").permitAll()
                .anyRequest().authenticated()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .addFilterBefore(new JwtAuthorizationFilter(authenticationManager(), tokenProvider), UsernamePasswordAuthenticationFilter.class)
            .addFilter(getJWTAuthenticationFilter());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.customUserDetailsService).passwordEncoder(this.passwordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> allowOrigins = Arrays.asList("http://192.168.0.101:8100", "http://localhost:8100", "http://localhost");
        configuration.setAllowedOrigins(allowOrigins);
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private JwtAuthenticationFilter getJWTAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(authenticationManager(), tokenProvider);
        authenticationFilter.setFilterProcessesUrl("/api/user/login");
        return authenticationFilter;
    }
}
