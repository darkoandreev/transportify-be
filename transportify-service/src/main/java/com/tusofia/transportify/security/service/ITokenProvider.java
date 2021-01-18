package com.tusofia.transportify.security.service;

import com.tusofia.transportify.security.dto.Token;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;

public interface ITokenProvider {
  Token generateAccessToken(String subject);

  Token generateRefreshToken(String subject);

  String getUsernameFromToken(String token);

  LocalDateTime getExpiryDateFromToken(String token);

  boolean validateToken(String token);

  UsernamePasswordAuthenticationToken getAuthentication(String cookieValue);
}
