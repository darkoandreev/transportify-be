package com.tusofia.transportify.transport.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

  @MessageMapping("/send")
  @SendTo("/topic/greetings")
  public String sendMessage(@Payload String message) {
    return message;
  }
}
