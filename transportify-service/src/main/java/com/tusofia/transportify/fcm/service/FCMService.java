package com.tusofia.transportify.fcm.service;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tusofia.transportify.fcm.model.NotificationParameterEnum;
import com.tusofia.transportify.fcm.model.PushNotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class FCMService {

  public void sendMessage(Map<String, String> data, PushNotificationRequest request)
          throws InterruptedException, ExecutionException {
    Message message = getPreconfiguredMessageWithData(data, request);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String jsonOutput = gson.toJson(message);
    String response = sendAndGetResponse(message);
    log.info("Sent message with data. Topic: {}, {} msg {}" + request.getTopic(), response, jsonOutput);
  }

  public void sendMessageWithoutData(PushNotificationRequest request)
          throws InterruptedException, ExecutionException {
    Message message = getPreconfiguredMessageWithoutData(request);
    String response = sendAndGetResponse(message);
    log.info("Sent message without data. Topic: {}, {}", request.getTopic(), response);
  }

  private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
    return FirebaseMessaging.getInstance().sendAsync(message).get();
  }

  private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
    AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
    ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
    Notification notification = Notification.builder().setTitle(request.getTitle()).setBody(request.getMessage()).build();
    return Message.builder()
            .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(notification);
  }

  private Message getPreconfiguredMessageWithData(Map<String, String> data, PushNotificationRequest request) {
    return getPreconfiguredMessageBuilder(request).putAllData(data).setToken(request.getToken())
            .build();
  }

  private Message getPreconfiguredMessageWithoutData(PushNotificationRequest request) {
    return getPreconfiguredMessageBuilder(request).setTopic(request.getTopic())
            .build();
  }

  private AndroidConfig getAndroidConfig(String topic) {
    return AndroidConfig.builder()
            .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
            .setPriority(AndroidConfig.Priority.HIGH)
            .setNotification(AndroidNotification.builder().setSound(NotificationParameterEnum.SOUND.getValue())
                    .setColor(NotificationParameterEnum.COLOR.getValue()).setTag(topic).build()).build();
  }

  private ApnsConfig getApnsConfig(String topic) {
    return ApnsConfig.builder()
            .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
  }
}
