package com.tusofia.transportify.vehicle.repository;

import com.tusofia.transportify.vehicle.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
  List<VehicleEntity> findAllByUserId(Long userId);
}
