package com.tusofia.transportify.transport.drive.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tusofia.transportify.transport.applicant.entity.ApplicantEntity;
import com.tusofia.transportify.user.entity.User;
import com.tusofia.transportify.vehicle.entity.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "DRIVE_TRANSPORT")
public class DriveTransportEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "DRIVE_TRANSPORT_ID")
  private Long id;

  @Column(name = "CITY_FROM")
  private String cityFrom;

  @Column(name = "CITY_TO")
  private String cityTo;

  @Column(name = "TRANSPORT_TIME")
  @JsonFormat(pattern = "HH:mm")
  private LocalTime transportTime;

  @Column(name = "TRANSPORT_DATE")
  private Date transportDate;

  @Column(name = "TRANSPORT_PRICE")
  private Long transportPrice;

  @Column(name = "AVAILABLE_SEATS")
  private Integer availableSeats;

  @Column(name = "ADDITIONAL_DETAILS")
  private String additionalDetails;

  @Column(name = "DISTANCE")
  private String distance;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "VEHICLE_ID", referencedColumnName = "VEHICLE_ID")
  private VehicleEntity vehicle;

  @OneToMany(mappedBy = "driveTransport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ApplicantEntity> applicants;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private User user;
}
