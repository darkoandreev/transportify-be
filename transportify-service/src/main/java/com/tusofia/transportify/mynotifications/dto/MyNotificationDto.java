package com.tusofia.transportify.mynotifications.dto;

import com.tusofia.transportify.fcm.model.NotificationTypeEnum;
import com.tusofia.transportify.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyNotificationDto {
  private Long id;

  private String message;

  private String returnUrl;

  private String title;

  private boolean isRead;

  private NotificationTypeEnum notificationTypeEnum;

  private User user;
}
