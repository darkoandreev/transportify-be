package com.tusofia.transportify.transport.drive.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class DriverTransportMappedParams {
  @NotEmpty
  private String cityFrom;

  @NotEmpty
  private String cityTo;

  @NotNull
  private Date transportDate;
}
