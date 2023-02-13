package com.example.springwithhibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class DefaultMailService {
    @Autowired
    private JavaMailSender mailSender;
    public SimpleMailMessage templateMessage;


    public void sendMessage(String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("eksoxxx777@gmail.com");
        message.setText("Hello world! " + msg);
        mailSender.send(message);
    }
}
