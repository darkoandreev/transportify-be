package tusofia.authservice.security.utils;

import javax.servlet.http.Cookie;

public class CookieUtil {
  private CookieUtil() {}

  public static Cookie createAccessTokenCookie(String token, long duration) {
    Cookie cookie = new Cookie("accessToken", token);
    cookie.setMaxAge((int) duration);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    return cookie;
  }
}
