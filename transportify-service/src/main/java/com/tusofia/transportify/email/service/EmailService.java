package com.tusofia.transportify.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
  private final JavaMailSender javaMailSender;

  @Autowired
  public EmailService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Async
  public void sendMail(SimpleMailMessage simpleMailMessage) {
    this.javaMailSender.send(simpleMailMessage);
  }

  public SimpleMailMessage createSimpleMailMessage(String email, String confirmationToken) {
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setTo(email);
    simpleMailMessage.setSubject("Complete Registration!");
    simpleMailMessage.setFrom("andreev.darko@gmail.com");
    simpleMailMessage.setText("To confirm your account, please click here : "
            +"http://localhost:8080/api/user/confirm-account?token=" + confirmationToken);

    return simpleMailMessage;
  }
}
