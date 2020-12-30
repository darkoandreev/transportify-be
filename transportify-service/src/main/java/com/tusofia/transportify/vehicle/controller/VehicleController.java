package com.tusofia.transportify.vehicle.controller;

import com.tusofia.transportify.vehicle.dto.VehicleDto;
import com.tusofia.transportify.vehicle.entity.VehicleEntity;
import com.tusofia.transportify.vehicle.service.VehicleService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

  @Autowired
  private VehicleService vehicleService;

  @PostMapping()
  public ResponseEntity<VehicleEntity> persistVehicle(
          @RequestBody @Valid VehicleDto vehicleDto,
          @RequestHeader("userId") Long userId) {
    return new ResponseEntity<>(this.vehicleService.persistVehicle(vehicleDto, userId), HttpStatus.CREATED);
  }

  @GetMapping()
  public ResponseEntity<List<VehicleEntity>> getVehicles(@RequestHeader("userId") Long userId) {
    return new ResponseEntity<>(this.vehicleService.getVehiclesByUserId(userId), HttpStatus.OK);
  }

  @GetMapping("/{vehicleId}")
  public ResponseEntity<VehicleEntity> getVehicleById(@PathVariable Long vehicleId) throws NotFoundException {
    return new ResponseEntity<>(this.vehicleService.getVehicleById(vehicleId), HttpStatus.OK);
  }

  @DeleteMapping("/{vehicleId}")
  public ResponseEntity<Void> deleteVehicle(@PathVariable Long vehicleId) {
    this.vehicleService.deleteById(vehicleId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping()
  public ResponseEntity<VehicleEntity> updateVehicle(@RequestBody VehicleDto vehicleDto) throws NotFoundException {
    return new ResponseEntity<>(this.vehicleService.updateVehicle(vehicleDto), HttpStatus.OK);
  }
}
