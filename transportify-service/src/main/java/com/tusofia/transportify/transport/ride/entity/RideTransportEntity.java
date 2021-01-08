package com.tusofia.transportify.transport.ride.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tusofia.transportify.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "RIDE_TRANSPORT")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RideTransportEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "CITY_FROM")
  private String cityFrom;

  @Column(name = "CITY_TO")
  private String cityTo;

  @Column(name = "TRANSPORT_DATE")
  private Date transportDate;

  @Column(name = "DISTANCE")
  private String distance;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnore
  private User user;
}
