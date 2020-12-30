package com.tusofia.transportify.transport.drive.dto;

import com.tusofia.transportify.user.entity.User;
import com.tusofia.transportify.vehicle.entity.VehicleEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

@Getter
@Setter
public class DriveTransportDto {
  private Long id;

  @NotNull
  private String cityFrom;

  @NotNull
  private String cityTo;

  @NotNull
  private LocalTime transportTime;

  @NotNull
  private Date transportDate;

  @NotNull
  private Long transportPrice;

  @NotNull
  private Integer availableSeats;

  private String distance;

  private String additionalDetails;

  private Long vehicleId;

  private VehicleEntity vehicle;

  private User user;
}
