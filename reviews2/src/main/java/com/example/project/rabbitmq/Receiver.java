package com.example.project.rabbitmq;
import com.example.project.model.Review;
import com.example.project.services.ReviewService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
public class Receiver {

    @Autowired
    private ReviewService service;

    @RabbitListener(queues = "reviews_Two")
    public void consumeMessage(Review review) {
        final var review2 = service.create(review);
        System.out.println("Message returned:" + review);
    }
}
