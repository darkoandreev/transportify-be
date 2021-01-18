package com.tusofia.transportify.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
@Setter
public class UserRatingOccurrenceResponse {
  private Map<Integer, Long> ratingOccurrences;
  private int userRating;
}
