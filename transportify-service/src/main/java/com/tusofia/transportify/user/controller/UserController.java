package com.tusofia.transportify.user.controller;

import com.tusofia.transportify.user.entity.User;
import com.tusofia.transportify.user.exceptions.UserAlreadyExistException;
import com.tusofia.transportify.user.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping()
  public User getUser(@RequestHeader("userId") Long userId) {
    return this.userService.findUserById(userId);
  }

  @PostMapping("signup")
  public ResponseEntity<User> registerUser(@RequestBody @Valid User user) throws UserAlreadyExistException {
    return new ResponseEntity<>(this.userService.registerUser(user), HttpStatus.ACCEPTED);
  }

  @PutMapping("user-details")
  public ResponseEntity<User> updateUserDetails(@RequestBody User user) {
    return new ResponseEntity<>(this.userService.updateUser(user), HttpStatus.ACCEPTED);
  }

  @GetMapping("confirm-account")
  public ResponseEntity<User> confirmAccount(@RequestParam String confirmationToken) throws NotFoundException {
    return new ResponseEntity<>(this.userService.confirmUserAccount(confirmationToken), HttpStatus.OK);
  }

  @GetMapping("user-details")
  public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
    return new ResponseEntity<>(this.userService.findUserByUsername(username), HttpStatus.OK);
  }

  @GetMapping("user-profile")
  public ResponseEntity<User> getUserProfileDetails(@RequestParam String username) {
    return new ResponseEntity<>(this.userService.getUserProfileDetails(username), HttpStatus.OK);
  }
}
