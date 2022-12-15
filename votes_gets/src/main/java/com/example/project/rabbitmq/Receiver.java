package com.example.project.rabbitmq;


import com.example.project.model.Review;
import com.example.project.model.Vote;
import com.example.project.repositories.ReviewRepository;
import com.example.project.repositories.VoteRepository;
import com.example.project.services.VoteService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Receiver {

    @Autowired
    private VoteService service;

    @Autowired
    private VoteRepository repository;

    @Autowired
    private ReviewRepository reviewRepository;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void consumeMessage(Vote vote) {

        Optional<Vote> optional = repository.findById(vote.getId());

        if(optional.isPresent()) {
            System.out.println("Vote Duplicated");
            return;
        }

        final Vote obj = Vote.newFrom(vote);

        repository.save(obj);
        System.out.println("Vote received:" + vote);
    }


    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void consumeMessageCreate(Review review) {

        Optional<Review> optional = reviewRepository.findByUUID(review.getUuid());

        if(optional.isPresent()) {
            System.out.println("Review Duplicated");
            return;
        }

        final Review obj = Review.newFrom(review);

        reviewRepository.save(obj);

        System.out.println("Review received:" + review);

    }


}
