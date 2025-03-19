package com.example.addressBookApp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @RabbitListener(queues = "addressBookQueue")
    public void receiveMessage(String message) {
        logger.info("Received message: {}", message);
    }
}
