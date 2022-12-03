package com.example.project.rabbitmq;

import com.example.project.model.Review;
import com.example.project.services.ReviewService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    @Autowired
    private ReviewService service;

    @RabbitListener(queues = "reviews_One")
    public void consumeMessage(Review review) {
        final var product2 = service.create(review);
        System.out.println("Message returned:" + review);
    }
}
