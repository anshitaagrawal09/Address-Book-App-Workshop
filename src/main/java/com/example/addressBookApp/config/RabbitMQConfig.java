package com.example.addressBookApp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue addressBookQueue() {
        return new Queue("addressBookQueue", true);
    }
}
