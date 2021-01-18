package com.tusofia.transportify.fcm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PushNotificationResponse {
  private int status;
  private String message;
}
