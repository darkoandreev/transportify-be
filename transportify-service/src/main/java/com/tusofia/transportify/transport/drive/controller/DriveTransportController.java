package com.tusofia.transportify.transport.drive.controller;

import com.tusofia.transportify.transport.drive.dto.DriveTransportDto;
import com.tusofia.transportify.transport.drive.entity.DriveTransportEntity;
import com.tusofia.transportify.transport.drive.request.DriverTransportMappedParams;
import com.tusofia.transportify.transport.drive.service.DriveTransportService;
import javassist.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
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

  @GetMapping("/mapped")
  public ResponseEntity<List<DriveTransportEntity>> getMappedDriveTransports(@Valid DriverTransportMappedParams params) {
    return new ResponseEntity<>(this.driveTransportService.getMappedDriveTransports(params), HttpStatus.OK);
  }
}
