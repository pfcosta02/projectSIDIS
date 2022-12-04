package com.example.project.rabbitmq;


import com.example.project.model.Vote;
import com.example.project.services.VoteService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    private VoteService service;

    @RabbitListener(queues = "vote_One")
    public void consumeMessage(Vote vote) {
        final var product2 = service.create(vote);
        System.out.println("Message returned:" + vote);
    }
}
