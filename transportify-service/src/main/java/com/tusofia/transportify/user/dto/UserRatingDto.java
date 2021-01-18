package com.tusofia.transportify.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserRatingDto {

  @NotNull
  private Long ratedUserId;

  private String comment;

  @NotNull
  @Min(1)
  @Max(5)
  private int value;
}
