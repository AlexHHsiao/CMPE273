package com.example.cpme273.wlb.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

  @Autowired private JavaMailSender javaMailSender;

  public void sendEmail(String to, String title, String text) {

    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(to);

    msg.setSubject(title);
    msg.setText(text);

    javaMailSender.send(msg);
  }
}
