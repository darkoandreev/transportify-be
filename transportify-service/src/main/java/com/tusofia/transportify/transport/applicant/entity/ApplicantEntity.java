package com.tusofia.transportify.transport.applicant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tusofia.transportify.transport.drive.entity.DriveTransportEntity;
import com.tusofia.transportify.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "APPLICANT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "APPLICANT_ID")
  private Long id;

  @Column(name = "APPLICANT_STATUS", columnDefinition = "varchar(32) default 'PENDING'")
  @Enumerated(EnumType.STRING)
  private ApplicantStatusEnum applicantStatus;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED_DATE")
  private Date startDate;

  @Column(name = "DRIVE_TRANSPORT_ID", insertable = false, updatable = false)
  private Long driveTransportId;

  @ManyToOne
  @JoinColumn(name = "rider_id")
  private User rider;

  @Column(name = "DRIVE_TRANSPORT_ID", updatable = false, insertable = false)
  private Long driverTransportId;

  @ManyToOne
  @JoinColumn(name = "DRIVE_TRANSPORT_ID")
  @JsonIgnore
  private DriveTransportEntity driveTransport;
}
