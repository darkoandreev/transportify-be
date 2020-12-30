package com.tusofia.transportify.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tusofia.transportify.transport.applicant.entity.ApplicantEntity;
import com.tusofia.transportify.transport.drive.entity.DriveTransportEntity;
import com.tusofia.transportify.transport.ride.entity.RideTransportEntity;
import com.tusofia.transportify.vehicle.entity.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable, UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID")
  private Long id;

  @Column(name = "EMAIL", nullable = false, unique = true)
  @NotBlank(message = "Email is required")
  private String email;

  @Column(name = "USERNAME", nullable = false, unique = true)
  @NotBlank(message = "Username is required")
  private String username;

  @Column(name = "PASSWORD", nullable = false)
  @NotBlank(message = "Password is required")
  private String password;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "DATE_OF_BIRTH")
  private Date dateOfBirth;

  @Column(name = "IMAGE_URL")
  private String imageUrl;

  @Column(name = "GENDER")
  @Enumerated(EnumType.STRING)
  private GenderEnum gender;

  @Column(name = "PHONE_NUMBER", unique = true)
  private String phoneNumber;

  @Column(name = "ADDITIONA_DETAILS")
  private String additionalDetails;

  @Column(name = "REGISTERED_ON")
  private LocalDateTime registeredOn;

  @Column(name = "LAST_SEEN")
  private LocalDateTime lastSeen;

  @Column(name = "IS_ENABLED", columnDefinition = "boolean default false")
  private boolean isEnabled;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  private List<VehicleEntity> vehicles;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  private List<RideTransportEntity> rideTransports;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  private List<DriveTransportEntity> driveTransport;

  @OneToMany(mappedBy = "rider", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<ApplicantEntity> riderApplicants;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.isEnabled;
  }
}

