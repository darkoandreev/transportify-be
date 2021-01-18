package com.tusofia.transportify.security.service;

import com.tusofia.transportify.user.entity.User;
import com.tusofia.transportify.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public User loadUserByUsername(String username) {
    return this.userRepository.findByUsername(username).orElseThrow();
  }
}
