package com.tusofia.transportify.transport.history.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "TRANSPORT_HISTORY_DRIVE_DETAILS")
public class TransportHistoryDriveDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "TRANSPORT_TIME")
  @JsonFormat(pattern = "HH:mm")
  private LocalTime transportTime;

  @Column(name = "TRANSPORT_PRICE")
  private Long transportPrice;

  @Column(name = "AVAILABLE_SEATS")
  private Integer availableSeats;

  @Column(name = "ADDITIONAL_DETAILS")
  private String additionalDetails;

  @OneToOne
  @MapsId
  @JoinColumn(name = "TRANSPORT_HISTORY_ID")
  @JsonIgnore
  private TransportHistory transportHistory;
}
