package com.tusofia.transportify.transport.applicant.service;

import com.tusofia.transportify.transport.applicant.dto.ApplicantDto;
import com.tusofia.transportify.transport.applicant.entity.ApplicantEntity;
import com.tusofia.transportify.transport.applicant.repository.IApplicantRepository;
import com.tusofia.transportify.transport.drive.entity.DriveTransportEntity;
import com.tusofia.transportify.transport.drive.service.DriveTransportService;
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

  public ApplicantEntity createApplicant(ApplicantDto applicantDto) throws NotFoundException {
    DriveTransportEntity driveTransportEntity = this.driveTransportService.findById(applicantDto.getDriveTransportId());
    User riderUser = this.userService.findUserById(applicantDto.getRiderId());

    ApplicantEntity applicantEntity = this.customMapper.toApplicantEntity(applicantDto);
    applicantEntity.setDriveTransport(driveTransportEntity);
    applicantEntity.setRider(riderUser);

    return this.applicantRepository.save(applicantEntity);
  }

}
