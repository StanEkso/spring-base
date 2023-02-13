package com.example.springwithhibernate.service;

import com.example.springwithhibernate.entity.UserEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class DefaultMailService {
    private static Logger logger = LogManager.getLogger(DefaultMailService.class);
    @Autowired
    private JavaMailSender mailSender;
    public SimpleMailMessage templateMessage;


    public void sendMessage(String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("eksoxxx777@gmail.com");
        message.setText("Hello world! " + msg);
        mailSender.send(message);
    }

    public void sendRegisterEmail(UserEntity user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Регистрация на сайте");
        message.setFrom("noreply@eksodev.com");
        message.setText("Спасибо за регистрацию, " + user.getUsername() + "!");
        try {
            mailSender.send(message);
            logger.log(Level.INFO, "Email was sent to " + user.getEmail() + "!");
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error while sending email!");
        }
    }
}
