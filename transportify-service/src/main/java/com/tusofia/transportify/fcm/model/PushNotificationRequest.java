package com.tusofia.transportify.fcm.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PushNotificationRequest {
  private String title;
  private String message;
  private String topic;
  private String token;
}
