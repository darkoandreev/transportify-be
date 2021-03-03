package com.tusofia.transportify.mynotifications.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tusofia.transportify.fcm.model.NotificationTypeEnum;
import com.tusofia.transportify.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "MY_NOTIFICATIONS")
public class MyNotificationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "MY_NOTIFICATIONS_ID")
  private Long id;

  @Column(name = "MESSAGE", nullable = false)
  private String message;

  @Column(name = "TITLE", nullable = false)
  private String title;

  @Column(name = "RETURN_URl", nullable = false)
  private String returnUrl;

  @Column(name = "IS_READ", columnDefinition="BOOLEAN DEFAULT false")
  @JsonProperty("isRead")
  private boolean isRead;

  @Column(name = "TYPE", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private NotificationTypeEnum notificationTypeEnum;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED_AT")
  private Date createdAt;

  @ManyToOne
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;
}
