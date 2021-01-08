package com.tusofia.transportify.transport.applicant.repository;

import com.tusofia.transportify.transport.applicant.entity.ApplicantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IApplicantRepository extends JpaRepository<ApplicantEntity, Long> {
  ApplicantEntity findByRiderIdAndDriveTransportId(Long riderId, Long driveTransportId);
}
