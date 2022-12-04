package com.example.project.rabbitmq;
import com.example.project.exceptions.MyResourceNotFoundException;
import com.example.project.model.Review;
import com.example.project.repositories.ReviewRepository;
import com.example.project.services.ReviewService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.UUID;

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

    @RabbitListener(queues = "update_reviews_Get_Two")
    public void updateReview(Review review) {
        final var review2 = repository.findByUUID(review.getUuid())
                .orElseThrow(() -> new MyResourceNotFoundException("Cannot update an object that does not yet exist"));

        review2.applyPatch(review, 0);

        repository.save(review2);

        System.out.println("Message returned:" + review2);
    }

    @RabbitListener(queues = "delete_reviews_Get_Two")
    public void deleteReview(UUID uuid) {
        repository.deleteByIdIfMatch(uuid,0);

        System.out.println("Message returned:" + uuid);
    }
}
