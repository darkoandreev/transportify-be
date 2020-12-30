package tusofia.authservice.security.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Token {
  private TokenType tokenType;
  private String tokenValue;
  private Long duration;
  private LocalDateTime expiryDate;

  public enum TokenType {
    ACCESS, REFRESH
  }
}
