package com.example.food_store.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.food_store.constant.AppConstant;
import com.example.food_store.service.ISendEmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendEmailService implements ISendEmailService {
    private final ObjectProvider<JavaMailSender> mailSenderProvider;
    @Value("${spring.mail.username:}")
    private String configuredFromEmail;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        JavaMailSender mailSender = mailSenderProvider.getIfAvailable();
        if (mailSender == null) {
            log.warn("Skipping email send because JavaMailSender is not configured. Check SPRING_MAIL_* settings.");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        String fromEmail = (configuredFromEmail == null || configuredFromEmail.isBlank())
                ? AppConstant.SYSTEM_EMAIL_SENDER
                : configuredFromEmail;
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        try {
            mailSender.send(message);
            log.info("Sent email to {} using from={}", toEmail, fromEmail);
        } catch (Exception ex) {
            log.error("Failed to send email to {} using from={}", toEmail, fromEmail, ex);
            throw ex;
        }

    }

}
