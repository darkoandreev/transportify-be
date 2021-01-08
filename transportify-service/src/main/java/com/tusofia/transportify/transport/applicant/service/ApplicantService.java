package com.tusofia.transportify.transport.applicant.service;

import com.tusofia.transportify.transport.applicant.dto.ApplicantDto;
import com.tusofia.transportify.transport.applicant.entity.ApplicantEntity;
import com.tusofia.transportify.transport.applicant.entity.ApplicantStatusEnum;
import com.tusofia.transportify.transport.applicant.exception.AlreadyAppliedException;
import com.tusofia.transportify.transport.applicant.repository.IApplicantRepository;
import com.tusofia.transportify.transport.drive.entity.DriveTransportEntity;
import com.tusofia.transportify.transport.drive.service.DriveTransportService;
import com.tusofia.transportify.transport.ride.entity.RideTransportEntity;
import com.tusofia.transportify.transport.ride.service.RideTransportService;
import com.tusofia.transportify.user.entity.User;
import com.tusofia.transportify.user.service.UserService;
import com.tusofia.transportify.util.CustomMapper;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicantService {
  @Autowired
  private IApplicantRepository applicantRepository;

  @Autowired
  private CustomMapper customMapper;

  @Autowired
  private UserService userService;

  @Autowired
  private DriveTransportService driveTransportService;

  public ApplicantEntity createApplicant(ApplicantDto applicantDto, Long riderId) throws NotFoundException {
    ApplicantEntity applicantEntity = this.applicantRepository.findByRiderIdAndDriveTransportId(riderId, applicantDto.getDriveTransportId());

    if (applicantEntity != null) {
      throw new AlreadyAppliedException("You've already applied for this transport!");
    }

    DriveTransportEntity driveTransportEntity = this.driveTransportService.reduceAvailableSeats(applicantDto.getDriveTransportId());

    if (driveTransportEntity.getAvailableSeats() == 0) {
      throw new AlreadyAppliedException("No available seats for this transport!");
    }

    User riderUser = this.userService.findUserById(riderId);

    applicantEntity = this.customMapper.toApplicantEntity(applicantDto);
    applicantEntity.setDriveTransport(driveTransportEntity);
    applicantEntity.setRider(riderUser);

    return this.applicantRepository.save(applicantEntity);
  }

  public ApplicantEntity updateApplicantStatus(ApplicantStatusEnum applicantStatusEnum, Long applicantId) {
    ApplicantEntity applicantEntity = this.applicantRepository.findById(applicantId).orElseThrow();

    applicantEntity.setApplicantStatus(applicantStatusEnum);

    return this.applicantRepository.save(applicantEntity);
  }

}
