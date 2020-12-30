package tusofia.authservice.security.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import tusofia.authservice.security.dto.Token;

import java.time.LocalDateTime;

public interface ITokenProvider {
  Token generateAccessToken(String subject);

  Token generateRefreshToken(String subject);

  String getUsernameFromToken(String token);

  LocalDateTime getExpiryDateFromToken(String token);

  boolean validateToken(String token);

  UsernamePasswordAuthenticationToken getAuthentication(String cookieValue);
}
