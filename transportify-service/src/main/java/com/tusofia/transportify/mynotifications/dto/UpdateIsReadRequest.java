package com.tusofia.transportify.mynotifications.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateIsReadRequest {
  private UpdateIsReadRequest() {}

  private List<Long> notificationIds;

  private Boolean isRead;
}
