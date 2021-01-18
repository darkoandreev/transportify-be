package com.tusofia.transportify.mynotifications.entity;

import com.tusofia.transportify.fcm.model.NotificationTypeEnum;
import com.tusofia.transportify.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "MY_NOTIFICATIONS")
public class MyNotifications {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "MY_NOTIFICATIONS_ID")
  private Long id;

  @Column(name = "MESSAGE", nullable = false)
  private String message;

  @Column(name = "RETURN_URl", nullable = false)
  private String returnUrl;

  @Column(name = "IS_READ", columnDefinition="BOOLEAN DEFAULT false")
  private boolean isRead;

  @Column(name = "TYPE", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private NotificationTypeEnum notificationTypeEnum;

  @ManyToOne
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;
}
