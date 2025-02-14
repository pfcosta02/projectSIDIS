package com.example.project.rabbitmq;


import com.example.project.exceptions.MyResourceNotFoundException;
import com.example.project.model.Review;
import com.example.project.model.Vote;
import com.example.project.repositories.ReviewRepository;
import com.example.project.repositories.VoteRepository;
import com.example.project.services.VoteService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        List<Vote> optional = repository.findVotesReviewPending(vote.getUuid());

        if(!optional.isEmpty()) {
            System.out.println("Vote Duplicated");
            return;
        }

        final Vote obj = Vote.newFrom(vote);

        repository.save(obj);

        System.out.println("Vote received:" + vote);
    }


    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
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

    @RabbitListener(queues = "#{autoDeleteQueue4.name}")
    public void updateReview(Review review) {
        final var review2 = reviewRepository.findByUUID(review.getUuid())
                .orElseThrow(() -> new MyResourceNotFoundException("Cannot update an object that does not yet exist"));

        review2.applyPatch(review, review2.getVersion());

        reviewRepository.save(review2);

        System.out.println("Review updated:" + review2);
    }

    @RabbitListener(queues = "#{autoDeleteQueue5.name}")
    public void deleteReview(UUID uuid) {
        reviewRepository.deleteByIdIfMatch(uuid,1);

        System.out.println("Review deleted:" + uuid);
    }

    @RabbitListener(queues = "#{autoDeleteQueue3.name}")
    public void updateVote(UUID uuid) {
        List<Vote> prod = repository.findVotesReviewPending(uuid);

        if(prod.isEmpty()) {
            return;
        }

        for (Vote aux: prod) {
            aux.applyPatch(aux.getVersion());

            repository.save(aux);
        }

        System.out.println("Votes  Updated");
    }


}
