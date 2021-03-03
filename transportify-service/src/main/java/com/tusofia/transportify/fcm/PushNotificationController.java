package com.tusofia.transportify.fcm;

import com.tusofia.transportify.fcm.entity.PushNotificationTokenEntity;
import com.tusofia.transportify.fcm.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/push-notification")
public class PushNotificationController {
  @Autowired
  private PushNotificationService pushNotificationService;

  @PostMapping()
  public ResponseEntity<Void> persist(
          @RequestBody PushNotificationTokenEntity pushNotificationTokenEntity,
          @RequestHeader Long userId) {
    this.pushNotificationService.persistToken(pushNotificationTokenEntity, userId);
    return ResponseEntity.ok().build();
  }
}
