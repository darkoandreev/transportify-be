package com.tusofia.transportify.transport.drive.service;

import com.tusofia.transportify.google.distance.DistanceService;
import com.tusofia.transportify.transport.applicant.dto.ApplicantDto;
import com.tusofia.transportify.transport.applicant.entity.ApplicantStatusEnum;
import com.tusofia.transportify.transport.drive.dto.DriveTransportDto;
import com.tusofia.transportify.transport.drive.entity.DriveTransportEntity;
import com.tusofia.transportify.transport.drive.repository.IDriveTransportRepository;
import com.tusofia.transportify.transport.drive.request.DriverTransportMappedParams;
import com.tusofia.transportify.transport.ride.service.RideTransportService;
import com.tusofia.transportify.transport.validation.ValidationService;
import com.tusofia.transportify.user.entity.User;
import com.tusofia.transportify.user.service.UserService;
import com.tusofia.transportify.util.CustomMapper;
import com.tusofia.transportify.vehicle.entity.VehicleEntity;
import com.tusofia.transportify.vehicle.service.VehicleService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DriveTransportService {

  @Autowired
  private IDriveTransportRepository driveTransportRepository;

  @Autowired
  private VehicleService vehicleService;

  @Autowired
  private UserService userService;

  @Autowired
  private DistanceService distanceService;

  @Autowired
  private ValidationService validationService;

  @Autowired
  private CustomMapper customMapper;

  public DriveTransportEntity persist(DriveTransportDto driveTransportDto, Long userId) throws NotFoundException {
    this.validationService.validateTransport(driveTransportDto.getTransportDate(), userId);

    VehicleEntity vehicleEntity = this.vehicleService.getVehicleById(driveTransportDto.getVehicleId());
    User user = this.userService.findUserById(userId);
    String distance = this.distanceService.getDistanceResult(driveTransportDto.getCityFrom(), driveTransportDto.getCityTo());
    driveTransportDto.setDistance(distance);
    driveTransportDto.setVehicle(vehicleEntity);
    driveTransportDto.setUser(user);

    return this.driveTransportRepository.save(this.customMapper.toDriveTransportEntity(driveTransportDto));
  }

  public List<DriveTransportEntity> searchDriveTransports(DriverTransportMappedParams params) {
    return this.driveTransportRepository
            .findAllByCityFromAndCityToAndTransportDateAndAvailableSeatsGreaterThanEqual(params.getCityFrom(), params.getCityTo(), params.getTransportDate(), 1);
  }

  public DriveTransportEntity changeAvailableSeats(Long driveTransportId, ApplicantStatusEnum applicantStatusEnum) throws NotFoundException {
    DriveTransportEntity driveTransportEntity = this.findById(driveTransportId);

    int availableSeats = applicantStatusEnum == ApplicantStatusEnum.ACCEPTED ? driveTransportEntity.getAvailableSeats() - 1 : driveTransportEntity.getAvailableSeats() + 1;

    driveTransportEntity.setAvailableSeats(availableSeats);
    return this.driveTransportRepository.save(driveTransportEntity);
  }

  public List<DriveTransportEntity> findAllDriveTransportsByUserId(Long userId) {
    return this.driveTransportRepository.findAllByUserId(userId);
  }

  public DriveTransportEntity findById(Long driveTransportId) throws NotFoundException {
    return this.driveTransportRepository.findById(driveTransportId)
            .orElseThrow(() -> new NotFoundException("Drive transport not found"));
  }

  public int countDriveTransports(Date date, Long userId) {
    return this.driveTransportRepository.countByTransportDateAndUserId(date, userId);
  }

  public DriveTransportEntity deleteById(Long id) throws NotFoundException {
    DriveTransportEntity driveTransportEntity = this.findById(id);
    this.driveTransportRepository.deleteById(id);

    return driveTransportEntity;
  }

  public List<DriveTransportEntity> getAllBeforeTodayDate() {
    return this.driveTransportRepository.findAllByTransportDateBefore(new Date());
  }
}
