package com.tusofia.transportify.util;

import com.tusofia.transportify.mynotifications.dto.MyNotificationDto;
import com.tusofia.transportify.mynotifications.entity.MyNotificationEntity;
import com.tusofia.transportify.transport.applicant.dto.ApplicantDto;
import com.tusofia.transportify.transport.applicant.entity.ApplicantEntity;
import com.tusofia.transportify.transport.drive.dto.DriveTransportDto;
import com.tusofia.transportify.transport.drive.entity.DriveTransportEntity;
import com.tusofia.transportify.transport.ride.dto.RideTransportDto;
import com.tusofia.transportify.transport.ride.entity.RideTransportEntity;
import com.tusofia.transportify.user.dto.UserRatingDto;
import com.tusofia.transportify.user.entity.UserRating;
import com.tusofia.transportify.vehicle.dto.VehicleDto;
import com.tusofia.transportify.vehicle.entity.VehicleEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomMapper {
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateVehicleFromDto(VehicleDto dto, @MappingTarget VehicleEntity entity);

  VehicleEntity toVehicleEntity(VehicleDto vehicleDto);
  RideTransportEntity toRideTransportEntity(RideTransportDto rideTransportDto);
  DriveTransportEntity toDriveTransportEntity(DriveTransportDto driveTransportDto);
  ApplicantEntity toApplicantEntity(ApplicantDto applicantDto);
  UserRating toUserRatingEntity(UserRatingDto userRatingDto);
  MyNotificationEntity toMyNotificationEntity(MyNotificationDto myNotificationDto);
}
