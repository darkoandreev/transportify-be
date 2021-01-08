package com.tusofia.transportify.transport.ride.dto;

import com.tusofia.transportify.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class RideTransportDto {
  private Long id;

  @NotNull
  private String cityFrom;

  @NotNull
  private String cityTo;

  @NotNull
  private Date transportDate;

  private String distance;

  private User user;
}
