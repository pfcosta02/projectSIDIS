package com.example.project.rabbitmq;

import com.example.project.model.Review;
import com.example.project.model.ReviewDTO;
import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;
import com.example.project.repositories.ReviewRepository;
import com.example.project.repositories.VoteRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Rpc {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ReviewRepository revRepo;

    @Autowired
    private VoteRepository voteRepo;

    public void helper(){
        List<Review> finalReviewList = new ArrayList<>();
        List<ReviewDTO> rev = (List<ReviewDTO>) amqpTemplate.convertSendAndReceive("rpc_rev", "key", "");

        if (rev.isEmpty()){ return; }

        for (ReviewDTO aux:rev){
            finalReviewList.add(new Review(aux.reviewId,aux.uuid,aux.text,aux.rating,aux.upVote,aux.downVote,aux.dataTime,aux.status,aux.productSku,aux.customerId,aux.funnyFact,aux.version));
        }
        for (Review auxAdd: finalReviewList){
            revRepo.save(auxAdd);
        }
    }

    public void helper2(){
        List<Vote> finalVoteList = new ArrayList<>();
        List<VoteDTO> vote = (List<VoteDTO>) amqpTemplate.convertSendAndReceive("rpc_vote", "key", "");

        if (vote.isEmpty()){ return; }

        for (VoteDTO aux: vote) {
            finalVoteList.add(new Vote(aux.vote, aux.uuid, aux.customerId));
        }
        for (Vote auxAdd: finalVoteList){
            voteRepo.save(auxAdd);
        }
    }
}
