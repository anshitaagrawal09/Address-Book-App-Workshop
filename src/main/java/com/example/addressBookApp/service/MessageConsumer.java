//UC15
package com.example.addressBookApp.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.example.addressBookApp.config.RabbitMQConfig.*;

@Service
@RequiredArgsConstructor
public class MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    private final EmailService emailService;

    @RabbitListener(queues = USER_REGISTRATION_QUEUE)
    public void receiveUserRegistrationMessage(String email) {
        logger.info("Received User Registration Message: {}", email);

        String subject = "Welcome to Our Service!";
        String emailBody = "Dear User,\n\nThank you for registering. We are excited to have you on board!";
        emailService.sendEmail(email, subject, emailBody);

        logger.info("User Registration Email Sent to: {}", email);
    }

    @RabbitListener(queues = CONTACT_ADDED_QUEUE)
    public void receiveContactAddedMessage(String contactDetails) {
        logger.info("Received Contact Added Message: {}", contactDetails);

        String subject = "New Contact Added";
        String emailBody = "A new contact has been added to your address book.";
        emailService.sendEmail("user@example.com", subject, emailBody);  // Replace with actual user email

        logger.info("Contact Added Email Sent");
    }
}



//UC14
//package com.example.addressBookApp.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MessageConsumer {
//
//    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
//
//    @RabbitListener(queues = "addressBookQueue")
//    public void receiveMessage(String message) {
//        logger.info("Received message: {}", message);
//    }
//}
