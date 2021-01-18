package com.tusofia.transportify.fcm.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class FCMConfiguration {
  @Value("${firebase.configuration.filePath}")
  private String filePath;
}
