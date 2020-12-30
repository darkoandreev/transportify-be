package com.tusofia.transportify.confirmation.token.entity;

import com.tusofia.transportify.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity(name = "CONFIRMATION_TOKEN")
@Data
@NoArgsConstructor
public class ConfirmationTokenEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="TOKEN_ID")
  private Long id;

  @Column(name="CONFIRMATION_TOKEN")
  private String confirmationToken;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;

  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private User user;

  public ConfirmationTokenEntity(User user) {
    this.user = user;
    this.createdDate = new Date();
    this.confirmationToken = UUID.randomUUID().toString();
  }
}
