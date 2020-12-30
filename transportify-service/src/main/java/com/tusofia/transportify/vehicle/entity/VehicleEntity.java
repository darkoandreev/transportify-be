package com.tusofia.transportify.vehicle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tusofia.transportify.transport.drive.entity.DriveTransportEntity;
import com.tusofia.transportify.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "VEHICLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "VEHICLE_ID")
  private Long id;

  @Column(name = "MODEL")
  private String model;

  @Column(name = "BRAND")
  private String brand;

  @Column(name = "IMAGE")
  private String image;

  @Column(name = "COLOR")
  private String color;

  @Column(name = "YEAR_OF_PRODUCTION")
  private int yearOfProduction;

  @Column(name = "NUMBER_OF_SEATS")
  private int numberOfSeats;

  @Column(name = "HAS_LUGGAGE_SPACE")
  private boolean hasLuggageSpace;

  @Column(name = "HAS_AIR_CONDITION")
  private boolean hasAirCondition;

  @Column(name = "IS_SMOKING_ALLOWED")
  private boolean smokingAllowed;

  @Column(name = "IS_FOOD_ALLOWED")
  private boolean foodAllowed;

  @Column(name = "IS_DRINK_ALLOWED")
  private boolean drinkAllowed;

  @Column(name = "IS_PET_ALLOWED")
  private boolean petAllowed;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnore
  private User user;

  @OneToMany(mappedBy = "vehicle")
  @JsonIgnore
  private List<DriveTransportEntity> driveTransportEntities;
}
