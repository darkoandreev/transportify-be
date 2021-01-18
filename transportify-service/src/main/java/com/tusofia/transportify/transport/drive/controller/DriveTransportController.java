package com.tusofia.transportify.transport.drive.controller;

import com.tusofia.transportify.fcm.model.PushNotificationRequest;
import com.tusofia.transportify.fcm.service.PushNotificationService;
import com.tusofia.transportify.transport.drive.dto.DriveTransportDto;
import com.tusofia.transportify.transport.drive.entity.DriveTransportEntity;
import com.tusofia.transportify.transport.drive.request.DriverTransportMappedParams;
import com.tusofia.transportify.transport.drive.service.DriveTransportService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/drive-transport")
public class DriveTransportController {

  @Autowired
  private DriveTransportService driveTransportService;

  @PostMapping("{vehicleId}")
  public ResponseEntity<DriveTransportEntity> persist(
          @RequestBody @Valid DriveTransportDto driveTransportDto,
          @RequestHeader Long userId
  ) throws NotFoundException {
    return new ResponseEntity<>(this.driveTransportService.persist(driveTransportDto, userId), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<DriveTransportEntity>> getAllByUserId(@RequestHeader Long userId) {
    return new ResponseEntity<>(this.driveTransportService.findAllDriveTransportsByUserId(userId), HttpStatus.OK);
  }

  @GetMapping("/{driveTransportId}")
  public ResponseEntity<DriveTransportEntity> getById(@PathVariable Long driveTransportId) throws NotFoundException {
    return new ResponseEntity<>(this.driveTransportService.findById(driveTransportId), HttpStatus.OK);
  }

  @GetMapping("/search")
  public ResponseEntity<List<DriveTransportEntity>> searchDriveTransports(@Valid DriverTransportMappedParams params) {
    return new ResponseEntity<>(this.driveTransportService.searchDriveTransports(params), HttpStatus.OK);
  }

  @DeleteMapping("{driveTransportId}")
  public ResponseEntity<DriveTransportEntity> deleteById(@PathVariable Long driveTransportId) throws NotFoundException {
    return new ResponseEntity<>(this.driveTransportService.deleteById(driveTransportId), HttpStatus.OK);
  }
}
