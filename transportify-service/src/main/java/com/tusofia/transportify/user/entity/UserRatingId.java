package com.tusofia.transportify.user.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class UserRatingId implements Serializable {
  @ManyToOne(optional = false)
  @JoinColumn(name = "RATED_USER_ID")
  private User ratedUser;

  @ManyToOne(optional = false)
  @JoinColumn(name = "RATER_USER_ID")
  private User raterUser;
}
