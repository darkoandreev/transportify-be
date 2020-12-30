package com.tusofia.transportify.transport.ride.controller;

import com.tusofia.transportify.transport.ride.dto.RideTransportDto;
import com.tusofia.transportify.transport.ride.entity.RideTransportEntity;
import com.tusofia.transportify.transport.ride.service.RideTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/ride-transport")
public class RideTransportController {
  @Autowired
  private RideTransportService rideTransportService;

  @PostMapping
  public ResponseEntity<RideTransportEntity> persistRideTransport(
          @RequestBody @Valid RideTransportDto rideTransportDto,
          @RequestHeader Long userId) {
    return new ResponseEntity<>(this.rideTransportService.persistRideTransport(rideTransportDto, userId), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<RideTransportEntity>> getAllByUserId(@RequestHeader Long userId) {
    return new ResponseEntity<>(this.rideTransportService.getAllByUserId(userId), HttpStatus.OK);
  }
}