package com.tusofia.transportify.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tusofia.transportify.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
public final class SecurityUtils {

  private SecurityUtils() {}

  public static User getCurrentUser() {
    ObjectMapper objectMapper = new ObjectMapper();
    Optional<String> userLoginPrincipal = SecurityUtils.getCurrentUserLogin();
    User currentUser = null;
    try {
      currentUser = objectMapper.readValue(userLoginPrincipal.orElseThrow(), User.class);
    } catch (Exception ex) {
      log.error("User parse error: {}", ex.getMessage());
    }

    return currentUser;
  }

  /**
   * Get the login of the current user.
   *
   * @return the login of the current user.
   */
  public static Optional<String> getCurrentUserLogin() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
  }

  private static String extractPrincipal(Authentication authentication) {
    if (authentication == null) {
      return null;
    } else if (authentication.getPrincipal() instanceof UserDetails) {
      UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
      return springSecurityUser.getUsername();
    } else if (authentication.getPrincipal() instanceof String) {
      return (String) authentication.getPrincipal();
    }
    return null;
  }

  /**
   * Get the JWT of the current user.
   *
   * @return the JWT of the current user.
   */
  public static Optional<String> getCurrentUserJWT() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional
            .ofNullable(securityContext.getAuthentication())
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
  }

  /**
   * Check if a user is authenticated.
   *
   * @return true if the user is authenticated, false otherwise.
   */
  public static boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null && getAuthorities(authentication).noneMatch(AuthoritiesConstants.ANONYMOUS::equals);
  }

  /**
   * If the current user has a specific authority (security role).
   * <p>
   * The name of this method comes from the {@code isUserInRole()} method in the Servlet API.
   *
   * @param authority the authority to check.
   * @return true if the current user has the authority, false otherwise.
   */
  public static boolean isCurrentUserInRole(String authority) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null && getAuthorities(authentication).anyMatch(authority::equals);
  }

  private static Stream<String> getAuthorities(Authentication authentication) {
    return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
  }
}