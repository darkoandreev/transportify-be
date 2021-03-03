package com.tusofia.transportify.mynotifications.controller;

import com.tusofia.transportify.mynotifications.dto.UpdateIsReadRequest;
import com.tusofia.transportify.mynotifications.entity.MyNotificationEntity;
import com.tusofia.transportify.mynotifications.service.MyNotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/my-notification")
public class MyNotificationController {

  @Autowired
  private MyNotificationsService myNotificationsService;

  @GetMapping
  public ResponseEntity<List<MyNotificationEntity>> getAll(@RequestHeader Long userId) {
    return ResponseEntity.ok(this.myNotificationsService.getAll(userId));
  }

  @PatchMapping
  public ResponseEntity<Integer> updateIsReadProperty(@RequestBody UpdateIsReadRequest updateIsReadRequest) {
    return ResponseEntity.ok(this.myNotificationsService.updateIsReadProperty(updateIsReadRequest));
  }
}
