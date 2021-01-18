package com.tusofia.transportify.fcm.model;

public enum NotificationTypeEnum {
  CHANGE_APPLICANT_STATUS("CHANGE_APPLICANT_STATUS"),
  APPLY_FOR_DRIVE("APPLY_FOR_DRIVE"),
  USER_RATE("USER_RATE");

  private String value;

  NotificationTypeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
