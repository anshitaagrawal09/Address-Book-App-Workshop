package com.example.addressBookApp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//UC15
@Configuration
public class RabbitMQConfig {

    public static final String USER_REGISTRATION_QUEUE = "user.registration.queue";
    public static final String CONTACT_ADDED_QUEUE = "contact.added.queue";
    public static final String EXCHANGE = "app.exchange";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue userRegistrationQueue() {
        return new Queue(USER_REGISTRATION_QUEUE, true);
    }

    @Bean
    public Queue contactAddedQueue() {
        return new Queue(CONTACT_ADDED_QUEUE, true);
    }

    @Bean
    public Binding bindUserQueue(Queue userRegistrationQueue, DirectExchange exchange) {
        return BindingBuilder.bind(userRegistrationQueue).to(exchange).with("user.register");
    }

    @Bean
    public Binding bindContactQueue(Queue contactAddedQueue, DirectExchange exchange) {
        return BindingBuilder.bind(contactAddedQueue).to(exchange).with("contact.add");
    }
}



//UC14
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConfig {
//    @Bean
//    public Queue addressBookQueue() {
//        return new Queue("addressBookQueue", true);
//    }
//}
