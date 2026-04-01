package com.example.food_store.messaging.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.food_store.constant.AppConstant;
import com.example.food_store.messaging.message.EmailRequest;
import com.example.food_store.service.impl.SendEmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailProducer {
    private final RabbitTemplate rabbitTemplate;
    private final SendEmailService sendEmailService;

    public void sendEmailToQueue(EmailRequest emailRequest) {
        try {
            rabbitTemplate.convertAndSend(
                    AppConstant.EXCHANGE,
                    AppConstant.ROUTING_KEY,
                    emailRequest);
            log.info("Queued email for {}", emailRequest.getToEmail());
        } catch (Exception ex) {
            log.error("RabbitMQ unavailable, falling back to direct email send for {}", emailRequest.getToEmail(), ex);
            sendEmailService.sendEmail(
                    emailRequest.getToEmail(),
                    emailRequest.getSubject(),
                    emailRequest.getBody());
        }
    }
}
