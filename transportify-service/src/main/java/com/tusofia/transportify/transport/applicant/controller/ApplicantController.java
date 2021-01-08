package com.tusofia.transportify.transport.applicant.controller;

import com.tusofia.transportify.transport.applicant.dto.ApplicantDto;
import com.tusofia.transportify.transport.applicant.entity.ApplicantEntity;
import com.tusofia.transportify.transport.applicant.entity.ApplicantStatusEnum;
import com.tusofia.transportify.transport.applicant.service.ApplicantService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/applicant")
public class ApplicantController {
  @Autowired
  private ApplicantService applicantService;

  @PostMapping
  public ResponseEntity<ApplicantEntity> createApplicant(
          @RequestBody @Valid ApplicantDto applicantDto,
          @RequestHeader Long userId) throws NotFoundException {
    return new ResponseEntity<>(this.applicantService.createApplicant(applicantDto, userId), HttpStatus.OK);
  }

  @PatchMapping("/status/{applicantId}")
  public ResponseEntity<ApplicantEntity> updateApplicantStatus(@RequestBody ApplicantDto applicantDto, @PathVariable Long applicantId) {
    return new ResponseEntity<>(this.applicantService.updateApplicantStatus(applicantDto.getApplicantStatus(), applicantId), HttpStatus.OK);
  }

}
