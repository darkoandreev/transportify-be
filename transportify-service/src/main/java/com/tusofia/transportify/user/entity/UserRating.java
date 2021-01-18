package com.tusofia.transportify.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USER_RATING")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRating implements Serializable {
  @EmbeddedId
  private UserRatingId id;

  @Column(name = "VALUE")
  private int value;

  @Column(name = "COMMENT")
  private String comment;

  @Column(name = "CREATED_AT", nullable = false, updatable = false)
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "UPDATED_AT")
  @UpdateTimestamp
  private Date updatedAt;
}
