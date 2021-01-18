package com.tusofia.transportify.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tusofia.transportify.user.dto.UserRatingDto;
import com.tusofia.transportify.user.entity.UserRating;
import com.tusofia.transportify.user.response.UserRatingOccurrenceResponse;
import com.tusofia.transportify.user.service.UserRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/user-rating")
public class UserRatingController {
  @Autowired
  private UserRatingService userRatingService;

  @PostMapping()
  public ResponseEntity<UserRating> persist(@RequestBody @Valid UserRatingDto userRatingDto, @RequestHeader Long userId) {
    return new ResponseEntity<>(this.userRatingService.persist(userRatingDto, userId), HttpStatus.OK);
  }

  @GetMapping("{ratingValue}")
  public ResponseEntity<List<UserRating>> getAllByUserId(@RequestParam(required = false) Long ratedUserId, @PathVariable Integer ratingValue) {
    return new ResponseEntity<>(this.userRatingService.getAllByRatingValue(ratedUserId, ratingValue), HttpStatus.OK);
  }

  @GetMapping("/occurrences")
  public ResponseEntity<UserRatingOccurrenceResponse> getAllOccurrences(@RequestParam(required = false) Long ratedUserId) {
    return new ResponseEntity<>(this.userRatingService.getRatingValueOccurrence(ratedUserId), HttpStatus.OK);
  }
}
