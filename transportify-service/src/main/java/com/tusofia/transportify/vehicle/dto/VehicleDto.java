package com.tusofia.transportify.vehicle.dto;

import com.tusofia.transportify.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VehicleDto {
  private Long id;

  @NotEmpty
  private String model;

  @NotEmpty
  private String brand;

  @NotEmpty
  private String color;

  @NotNull
  private int numberOfSeats;

  private String image;

  private int yearOfProduction;

  private boolean hasLuggageSpace;

  private boolean hasAirCondition;

  private boolean smokingAllowed;

  private boolean foodAllowed;

  private boolean drinkAllowed;

  private boolean petAllowed;

  private User user;
}
