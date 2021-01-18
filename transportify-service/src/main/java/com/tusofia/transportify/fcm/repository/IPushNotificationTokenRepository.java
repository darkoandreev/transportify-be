package com.tusofia.transportify.fcm.repository;

import com.tusofia.transportify.fcm.entity.PushNotificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPushNotificationTokenRepository extends JpaRepository<PushNotificationTokenEntity, Long> {
}
