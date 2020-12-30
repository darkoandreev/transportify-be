package com.tusofia.transportify.transport.validation;

import com.tusofia.transportify.transport.drive.service.DriveTransportService;
import com.tusofia.transportify.transport.exception.TransportAlreadyExists;
import com.tusofia.transportify.transport.ride.service.RideTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ValidationService {

  @Autowired
  private DriveTransportService driveTransportService;

  @Autowired
  private RideTransportService rideTransportService;

  public void validateTransport(Date date, Long userId) {
    int countRideTransports = this.rideTransportService.countRideTransports(date, userId);
    int countDriveTransports = this.driveTransportService.countDriveTransports(date, userId);

    if (countDriveTransports > 0 || countRideTransports > 0) {
      throw new TransportAlreadyExists("You already have a transport for this date");
    }
  }
}
