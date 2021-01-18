package com.tusofia.transportify.transport.drive.repository;

import com.tusofia.transportify.transport.drive.entity.DriveTransportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface IDriveTransportRepository extends JpaRepository<DriveTransportEntity, Long> {
  List<DriveTransportEntity> findAllByUserId(Long userId);
  int countByTransportDateAndUserId(Date startDate, Long userId);
  List<DriveTransportEntity> findAllByCityFromAndCityToAndTransportDateAndAvailableSeatsGreaterThanEqual(String cityFrom, String cityTo, Date transportDate, Integer availableSeats);
  List<DriveTransportEntity> findAllByTransportDateBefore(Date today);
}
