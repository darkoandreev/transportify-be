package com.tusofia.transportify.fcm.service;

import com.tusofia.transportify.fcm.entity.PushNotificationTokenEntity;
import com.tusofia.transportify.fcm.model.NotificationTypeEnum;
import com.tusofia.transportify.fcm.model.PushNotificationRequest;
import com.tusofia.transportify.fcm.repository.IPushNotificationTokenRepository;
import com.tusofia.transportify.mynotifications.dto.MyNotificationDto;
import com.tusofia.transportify.mynotifications.service.MyNotificationsService;
import com.tusofia.transportify.user.entity.User;
import com.tusofia.transportify.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class PushNotificationService {
  @Autowired
  private FCMService fcmService;

  @Autowired
  private IPushNotificationTokenRepository pushNotificationTokenRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private MyNotificationsService myNotificationsService;

  public void sendPushNotification(Long sendToUserId, String title, String message, Map<String, String> data) {
    try {
      User user = this.userService.findUserById(sendToUserId);
      PushNotificationRequest pushNotificationRequest = PushNotificationRequest.builder()
              .token(user.getPushNotificationToken().getToken())
              .title(title)
              .message(message)
              .build();
      fcmService.sendMessage(data, pushNotificationRequest);

      MyNotificationDto myNotificationDto = MyNotificationDto.builder()
              .message(message)
              .notificationTypeEnum(NotificationTypeEnum.valueOf(data.get("type")))
              .isRead(false)
              .returnUrl(data.get("returnUrl"))
              .title(title)
              .user(user)
              .build();

      myNotificationsService.persistMyNotification(myNotificationDto);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  public void persistToken(PushNotificationTokenEntity tokenEntity, Long userId) {
    User user = this.userService.findUserById(userId);
    if (user.getPushNotificationToken() != null && StringUtils.hasText(user.getPushNotificationToken().getToken())) {
      PushNotificationTokenEntity foundToken = this.pushNotificationTokenRepository.findById(user.getPushNotificationToken().getId()).orElseThrow();
      foundToken.setToken(tokenEntity.getToken());
      this.pushNotificationTokenRepository.save(foundToken);

      return;
    }
    tokenEntity.setUser(user);
    user.setPushNotificationToken(tokenEntity);

    this.pushNotificationTokenRepository.save(tokenEntity);
  }
}
