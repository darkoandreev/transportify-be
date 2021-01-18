package com.tusofia.transportify.transport.history.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tusofia.transportify.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "TRANSPORT_HISTORY")
public class TransportHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TRANSPORT_HISTORY_ID")
  private Long id;

  @Column(name = "CITY_FROM")
  private String cityFrom;

  @Column(name = "CITY_TO")
  private String cityTo;

  @Column(name = "TRANSPORT_DATE")
  private Date transportDate;

  @Column(name = "IS_DRVE")
  private boolean isDrive;

  @ManyToOne
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  @OneToOne(mappedBy = "transportHistory", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private TransportHistoryDriveDetails transportHistoryDriveDetails;
}
