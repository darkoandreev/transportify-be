package tusofia.authservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tusofia.authservice.security.service.JwtAuthenticationFilter;

@FeignClient("transportify-service")
@RequestMapping("/api/user")
public interface IUserServiceClient {

  @GetMapping("user-details")
  JwtAuthenticationFilter.User getUserDetailsByUsername(@RequestParam String username);
}
