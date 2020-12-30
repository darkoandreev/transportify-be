package com.tusofia.transportify.transport.ride.service;

import com.tusofia.transportify.google.distance.IDistanceService;
import com.tusofia.transportify.transport.exception.TransportAlreadyExists;
import com.tusofia.transportify.transport.ride.dto.RideTransportDto;
import com.tusofia.transportify.transport.ride.entity.RideTransportEntity;
import com.tusofia.transportify.transport.ride.repository.IRideTransportRepository;
import com.tusofia.transportify.transport.validation.ValidationService;
import com.tusofia.transportify.user.entity.User;
import com.tusofia.transportify.user.service.UserService;
import com.tusofia.transportify.util.CustomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RideTransportService {
  @Autowired
  private IRideTransportRepository rideTransportRepository;

  @Autowired
  private CustomMapper customMapper;

  @Autowired
  private UserService userService;

  @Autowired
  private ValidationService validationService;

  @Autowired
  private IDistanceService distanceService;

  public RideTransportEntity persistRideTransport(RideTransportDto rideTransportDto, Long userId) {
    this.validationService.validateTransport(rideTransportDto.getTransportDate(), userId);

    User user = this.userService.findUserById(userId);
    rideTransportDto.setUser(user);
    String distance = this.distanceService.getDistanceResult(rideTransportDto.getCityFrom(), rideTransportDto.getCityTo());
    rideTransportDto.setDistance(distance);

    RideTransportEntity rideTransportEntity = this.customMapper.toRideTransportEntity(rideTransportDto);

    return this.rideTransportRepository.save(rideTransportEntity);
  }

  public int countRideTransports(Date date, Long userId) {
    return this.rideTransportRepository.countByTransportDateAndUserId(date, userId);
  }

  public List<RideTransportEntity> getAllByUserId(Long userId) {
    return this.rideTransportRepository.findAllByUserId(userId);
  }
}
