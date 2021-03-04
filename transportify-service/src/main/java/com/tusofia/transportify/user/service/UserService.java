package com.tusofia.transportify.user.service;

import com.tusofia.transportify.confirmation.token.entity.ConfirmationTokenEntity;
import com.tusofia.transportify.confirmation.token.service.ConfirmationTokenService;
import com.tusofia.transportify.email.service.EmailService;
import com.tusofia.transportify.user.entity.User;
import com.tusofia.transportify.user.exceptions.UserAlreadyExistException;
import com.tusofia.transportify.user.exceptions.UserNotFoundException;
import com.tusofia.transportify.user.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private EmailService emailService;

  @Autowired
  private ConfirmationTokenService confirmationTokenService;

  public User registerUser(User user) throws UserAlreadyExistException {
    if (this.userRepository.existsByUsername(user.getUsername())) {
      throw new UserAlreadyExistException("User already exists!");
    }


    user.setRegisteredOn(new Date());

    user.setPassword(this.passwordEncoder.encode(user.getPassword()));
    User savedUser = this.userRepository.save(user);

    ConfirmationTokenEntity confirmationTokenEntity = new ConfirmationTokenEntity(savedUser);
    ConfirmationTokenEntity savedConfirmationToken = this.confirmationTokenService.persist(confirmationTokenEntity);

    SimpleMailMessage simpleMailMessage = this.emailService.createSimpleMailMessage(user.getEmail(), savedConfirmationToken.getConfirmationToken());
    this.emailService.sendMail(simpleMailMessage);
    return savedUser;
  }

  public User updateUser(User newUser) {
    return this.userRepository.findById(newUser.getId())
            .map(user -> {
              this.replaceUserDetails(user, newUser);
              return this.userRepository.save(user);
            })
            .orElseThrow();
  }

  public User findUserById(Long userId) {
    return this.userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(String.format("User with userId: %s not found", userId)));
  }

  public User confirmUserAccount(String confirmationToken) throws NotFoundException {
    ConfirmationTokenEntity confirmationTokenEntity = this.confirmationTokenService.findByConfirmationToken(confirmationToken);

    if (confirmationTokenEntity == null) {
      throw new NotFoundException("The token is invalid or broken");
    }

    User user = this.findUserById(confirmationTokenEntity.getUser().getId());
    user.setEnabled(true);
    return this.userRepository.save(user);
  }

  public User findUserByUsername(String username) {
    Optional<User> applicationUser = this.userRepository.findByUsername(username);

    if (applicationUser.isEmpty()) {
      throw new UsernameNotFoundException(username);
    }

    return applicationUser.get();
  }

  public User getUserProfileDetails(String username) {
    return this.userRepository.getUserProfileDetailsByUsername(username);
  }

  public void updateCurrentRating(Integer rating, Long userId) {
    User user = this.findUserById(userId);
    user.setCurrentRating(rating);

    this.userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<User> applicationUser = this.userRepository.findByUsername(username);

    if (applicationUser.isEmpty()) {
      throw new UsernameNotFoundException(username);
    }

    return applicationUser.get();
  }

  private void replaceUserDetails(User user, User newUser) {
    user.setFirstName(newUser.getFirstName());
    user.setLastName(newUser.getLastName());
    user.setGender(newUser.getGender());
    user.setAdditionalDetails(newUser.getAdditionalDetails());
    user.setPhoneNumber(newUser.getPhoneNumber());
    user.setDateOfBirth(newUser.getDateOfBirth());
    user.setImageUrl(newUser.getImageUrl());
  }
}
