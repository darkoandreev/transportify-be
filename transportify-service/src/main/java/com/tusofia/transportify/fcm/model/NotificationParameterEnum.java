package com.tusofia.transportify.fcm.model;

public enum NotificationParameterEnum {
  SOUND("default"),
  COLOR("#FFFF00");

  private String value;

  NotificationParameterEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
