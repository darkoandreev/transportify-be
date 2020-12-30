package tusofia.authservice.security.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
@Getter
public class TokenConfiguration {
  @Value("${authentication.tokenSecret}")
  private String tokenSecret;

  @Value("${authentication.tokenExpirationInMs}")
  private Long tokenExpirationInMs;

  @Value("${authentication.accessTokenCookieName}")
  private String accessTokenCookieName;
}
