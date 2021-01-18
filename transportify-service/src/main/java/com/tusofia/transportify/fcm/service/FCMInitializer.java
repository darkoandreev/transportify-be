package com.tusofia.transportify.fcm.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.tusofia.transportify.fcm.config.FCMConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class FCMInitializer {
  @Autowired
  private FCMConfiguration fcmConfiguration;

  @PostConstruct
  public void initialize() {
    try {
      File file = ResourceUtils.getFile(this.fcmConfiguration.getFilePath());
      InputStream in = new FileInputStream(file);
      FirebaseOptions firebaseOptions = FirebaseOptions.builder()
              .setCredentials(GoogleCredentials.fromStream(in))
              .build();

      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(firebaseOptions);
        log.info("Firebase application has been initialized");
      }
    } catch (IOException e) {
      log.error("Firebase application throws an error: {}", e.getMessage());
    }
  }
}
