package com.chenemiken.sbhackathonstartpack.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl {
  @Autowired
  private JavaMailSender  mailSender;

  public void sendSimpleMessage(){
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("chenemiken15@gmail.com");
    message.setTo("kenneth.akor@eng.uniben.edu");
    message.setSubject("Test sb mail");
    message.setText("Hello world mail!");

    mailSender.send(message);
  }
}
