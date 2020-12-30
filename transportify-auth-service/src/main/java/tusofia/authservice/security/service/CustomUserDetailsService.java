package tusofia.authservice.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import tusofia.authservice.feign.IUserServiceClient;

@Component
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private IUserServiceClient userServiceClient;

  @Override
  public JwtAuthenticationFilter.User loadUserByUsername(String username) {
    return this.userServiceClient.getUserDetailsByUsername(username);
  }
}
