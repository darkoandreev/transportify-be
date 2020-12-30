package com.tusofia.transportify.transport.ride.repository;

import com.tusofia.transportify.transport.ride.entity.RideTransportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface IRideTransportRepository extends JpaRepository<RideTransportEntity, Long> {
  List<RideTransportEntity> findAllByUserId(Long userId);
  int countByTransportDateAndUserId(Date startDate, Long userId);
}
