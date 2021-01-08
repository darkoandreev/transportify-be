package com.tusofia.transportify.transport.applicant.dto;

import com.tusofia.transportify.transport.applicant.entity.ApplicantStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ApplicantDto {
  private ApplicantStatusEnum applicantStatus = ApplicantStatusEnum.PENDING;

  @NotNull
  private Long driveTransportId;
}
