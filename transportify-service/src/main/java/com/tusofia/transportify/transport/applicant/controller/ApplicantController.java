package com.tusofia.transportify.transport.applicant.controller;

import com.tusofia.transportify.transport.applicant.dto.ApplicantDto;
import com.tusofia.transportify.transport.applicant.entity.ApplicantEntity;
import com.tusofia.transportify.transport.applicant.service.ApplicantService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/applicant")
public class ApplicantController {
  @Autowired
  private ApplicantService applicantService;

  @PostMapping
  public ResponseEntity<ApplicantEntity> createApplicant(@RequestBody @Valid ApplicantDto applicantDto) throws NotFoundException {
    return new ResponseEntity<>(this.applicantService.createApplicant(applicantDto), HttpStatus.OK);
  }
}
