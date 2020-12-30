package com.tusofia.transportify.vehicle.service;

import com.tusofia.transportify.user.entity.User;
import com.tusofia.transportify.user.service.UserService;
import com.tusofia.transportify.util.CustomMapper;
import com.tusofia.transportify.vehicle.dto.VehicleDto;
import com.tusofia.transportify.vehicle.entity.VehicleEntity;
import com.tusofia.transportify.vehicle.repository.VehicleRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
  @Autowired
  private VehicleRepository vehicleRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private CustomMapper customMapper;

  public VehicleEntity persistVehicle(VehicleDto vehicleDto, Long userId) {
    User user = this.userService.findUserById(userId);
    vehicleDto.setUser(user);
    VehicleEntity vehicleEntity = this.customMapper.toVehicleEntity(vehicleDto);
    return this.vehicleRepository.save(vehicleEntity);
  }

  public VehicleEntity updateVehicle(VehicleDto vehicleDto) throws NotFoundException {
    VehicleEntity vehicle = this.getVehicleById(vehicleDto.getId());

    this.customMapper.updateVehicleFromDto(vehicleDto, vehicle);
    return this.vehicleRepository.saveAndFlush(vehicle);
  }

  public List<VehicleEntity> getVehiclesByUserId(Long userId) {
    return this.vehicleRepository.findAllByUserId(userId);
  }

  public VehicleEntity getVehicleById(Long vehicleId) throws NotFoundException {
    return this.vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new NotFoundException("Vehicle not found"));
  }

  public void deleteById(Long id) {
    this.vehicleRepository.deleteById(id);
  }
}
