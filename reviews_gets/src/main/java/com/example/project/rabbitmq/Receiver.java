package com.example.project.rabbitmq;
import com.example.project.model.Review;
import com.example.project.repositories.ReviewRepository;
import com.example.project.services.ReviewService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
public class Receiver {

    @Autowired
    private ReviewRepository repository;

    @RabbitListener(queues = "reviews_Get_One")
    public void consumeMessage(Review review) {
        final Review obj = Review.newFrom(review);
        repository.save(obj);
        System.out.println("Message returned:" + review);
    }
}
