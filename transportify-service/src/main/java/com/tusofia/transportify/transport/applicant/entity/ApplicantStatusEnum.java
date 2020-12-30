package com.tusofia.transportify.transport.applicant.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ApplicantStatusEnum {
  PENDING("PENDING"), ACCEPTED("ACCEPTED"), REJECTED("REJECTED"), CANCELED("CANCELED");

  private final String value;

  ApplicantStatusEnum (String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ApplicantStatusEnum fromValue(String value) {
    for (ApplicantStatusEnum b : ApplicantStatusEnum.values()) {
      if (b.value.equalsIgnoreCase(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
