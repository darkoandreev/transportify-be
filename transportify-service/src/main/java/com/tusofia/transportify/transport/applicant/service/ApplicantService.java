package com.tusofia.transportify.transport.applicant.service;

import com.tusofia.transportify.fcm.model.NotificationTypeEnum;
import com.tusofia.transportify.fcm.service.PushNotificationService;
import com.tusofia.transportify.transport.applicant.dto.ApplicantDto;
import com.tusofia.transportify.transport.applicant.entity.ApplicantEntity;
import com.tusofia.transportify.transport.applicant.entity.ApplicantStatusEnum;
import com.tusofia.transportify.transport.applicant.exception.AlreadyAppliedException;
import com.tusofia.transportify.transport.applicant.repository.IApplicantRepository;
import com.tusofia.transportify.transport.drive.entity.DriveTransportEntity;
import com.tusofia.transportify.transport.drive.service.DriveTransportService;
import com.tusofia.transportify.user.entity.User;
import com.tusofia.transportify.user.service.UserService;
import com.tusofia.transportify.util.CustomMapper;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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

  @Autowired
  private PushNotificationService pushNotificationService;

  public ApplicantEntity createApplicant(ApplicantDto applicantDto, Long riderId) throws NotFoundException {
    ApplicantEntity applicantEntity = this.applicantRepository.findByRiderIdAndDriveTransportId(riderId, applicantDto.getDriveTransportId());

    if (applicantEntity != null) {
      throw new AlreadyAppliedException("You've already applied for this transport!");
    }

    DriveTransportEntity driveTransportEntity = this.driveTransportService.findById(applicantDto.getDriveTransportId());

    if (driveTransportEntity.getAvailableSeats() == 0) {
      throw new AlreadyAppliedException("No available seats for this transport!");
    }

    User riderUser = this.userService.findUserById(riderId);

    applicantEntity = this.customMapper.toApplicantEntity(applicantDto);
    applicantEntity.setDriveTransport(driveTransportEntity);
    applicantEntity.setDriverTransportId(driveTransportEntity.getUser().getId());
    applicantEntity.setRider(riderUser);

    Map<String, String> data = Map.of("returnUrl", String.format("tabs/transports/drive-transport/%s", driveTransportEntity.getId()), "type", NotificationTypeEnum.APPLY_FOR_DRIVE.getValue());
    this.pushNotificationService.sendPushNotification(
            driveTransportEntity.getUser().getId(),
            "New passenger",
            String.format("%s wants to ride with you to %s on %s.", riderUser.getUsername(), driveTransportEntity.getCityTo(), driveTransportEntity.getTransportDate().toString()),
            data);

    return this.applicantRepository.save(applicantEntity);
  }

  public ApplicantEntity updateApplicantStatus(ApplicantStatusEnum applicantStatusEnum, Long applicantId) throws NotFoundException {
    ApplicantEntity applicantEntity = this.applicantRepository.findById(applicantId).orElseThrow();

  if (applicantStatusEnum == ApplicantStatusEnum.ACCEPTED) {
      DriveTransportEntity driveTransportEntity = this.driveTransportService.changeAvailableSeats(applicantEntity.getDriveTransportId());
      applicantEntity.setDriveTransport(driveTransportEntity);
    }

    applicantEntity.setApplicantStatus(applicantStatusEnum);
    if (applicantStatusEnum == ApplicantStatusEnum.ACCEPTED || applicantStatusEnum == ApplicantStatusEnum.REJECTED) {
      String message = applicantStatusEnum == ApplicantStatusEnum.ACCEPTED ? "You are accepted! Click to see the ride." : "You're rejected! Please find another ride.";
      Map<String, String> data = Map.of(
              "returnUrl",
              String.format("/tabs/transports/drive-transport/%s",
                      applicantEntity.getDriveTransport().getId()),
              "type",
              NotificationTypeEnum.CHANGE_APPLICANT_STATUS.getValue());
      this.pushNotificationService.sendPushNotification(applicantEntity.getRider().getId(), "Ride status changed", message, data);
    }
    return this.applicantRepository.save(applicantEntity);
  }

}
