package com.tusofia.transportify.mynotifications.service;

import com.tusofia.transportify.mynotifications.dto.MyNotificationDto;
import com.tusofia.transportify.mynotifications.dto.UpdateIsReadRequest;
import com.tusofia.transportify.mynotifications.entity.MyNotificationEntity;
import com.tusofia.transportify.mynotifications.repository.IMyNotificationsRepository;
import com.tusofia.transportify.util.CustomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyNotificationsService {

  @Autowired
  private IMyNotificationsRepository myNotificationsRepository;

  @Autowired
  private CustomMapper customMapper;

  public MyNotificationEntity persistMyNotification(MyNotificationDto myNotificationDto) {
    return this.myNotificationsRepository.save(this.customMapper.toMyNotificationEntity(myNotificationDto));
  }

  public List<MyNotificationEntity> getAll(Long userId) {
    return this.myNotificationsRepository.findAllByUserIdOrderByIsReadAscCreatedAtDesc(userId);
  }

  public Integer updateIsReadProperty(UpdateIsReadRequest updateIsReadRequest) {
    return this.myNotificationsRepository.updateNotificationIsRead(updateIsReadRequest.getIsRead(), updateIsReadRequest.getNotificationIds());
  }
}
