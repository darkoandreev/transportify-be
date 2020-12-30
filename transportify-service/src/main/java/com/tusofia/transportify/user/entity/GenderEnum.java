package com.tusofia.transportify.user.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GenderEnum {

  MALE("Male"), FEMALE("Female");

  private String value;

  GenderEnum (String v) {
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
  public static GenderEnum fromValue(String value) {
    for (GenderEnum b : GenderEnum.values()) {
      if (b.value.equalsIgnoreCase(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
