package com.tusofia.transportify.mynotifications.repository;

import com.tusofia.transportify.mynotifications.entity.MyNotificationEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface IMyNotificationsRepository extends CrudRepository<MyNotificationEntity, Long> {
  List<MyNotificationEntity> findAllByUserIdOrderByIsReadAscCreatedAtDesc(Long userId);

  @Modifying
  @Transactional
  @Query("UPDATE MyNotificationEntity notification SET notification.isRead = ?1 WHERE notification.id in ?2")
  Integer updateNotificationIsRead(Boolean isRead, List<Long> notificationIds);
}
