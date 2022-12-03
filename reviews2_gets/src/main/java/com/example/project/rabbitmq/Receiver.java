package com.example.project.rabbitmq;
import com.example.project.model.Review;
import com.example.project.repositories.ReviewRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    private ReviewRepository repository;

    @RabbitListener(queues = "reviews_Get_Two")
    public void consumeMessage(Review review) {
        final Review obj = Review.newFrom(review);
        repository.save(obj);
        System.out.println("Message returned:" + review);
    }
}
