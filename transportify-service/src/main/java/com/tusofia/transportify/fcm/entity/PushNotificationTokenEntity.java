package com.tusofia.transportify.fcm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tusofia.transportify.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "PUSH_NOTIFICATION_TOKEN")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PushNotificationTokenEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TOKEN_ID")
  private Long id;

  @Column(name = "TOKEN")
  private String token;

  @Column(name = "CREATED_AT")
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "UPDATED_AT")
  @UpdateTimestamp
  private Date updatedAt;

  @OneToOne(mappedBy = "pushNotificationToken", cascade = CascadeType.ALL)
  @JsonIgnore
  private User user;
}
