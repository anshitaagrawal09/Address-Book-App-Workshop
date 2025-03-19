//UC15
package com.example.addressBookApp.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.example.addressBookApp.config.RabbitMQConfig.*;

@Service
@RequiredArgsConstructor
public class MessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public void publishUserRegistrationEvent(String email) {
        logger.info("Publishing User Registration Event for email: {}", email);
        rabbitTemplate.convertAndSend(EXCHANGE, "user.register", email);
    }

    public void publishContactAddedEvent(String contactDetails) {
        logger.info("Publishing Contact Added Event: {}", contactDetails);
        rabbitTemplate.convertAndSend(EXCHANGE, "contact.add", contactDetails);
    }
}


//UC14
//package com.example.addressBookApp.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MessageProducer {
//
//    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    public void sendMessage(String message) {
//        logger.info("Sending message: {}", message);
//        rabbitTemplate.convertAndSend("addressBookQueue", message);
//    }
//}
