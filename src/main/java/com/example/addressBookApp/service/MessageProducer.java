package com.example.addressBookApp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        logger.info("Sending message: {}", message);
        rabbitTemplate.convertAndSend("addressBookQueue", message);
    }
}
