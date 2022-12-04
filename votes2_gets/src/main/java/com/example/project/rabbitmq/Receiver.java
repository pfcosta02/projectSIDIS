package com.example.project.rabbitmq;
import com.example.project.model.Vote;
import com.example.project.repositories.VoteRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    private VoteRepository repository;

    @RabbitListener(queues = "vote_Get_Two")
    public void consumeMessage(Vote review) {
        final Vote obj = Vote.newFrom(review);
        repository.save(obj);
        System.out.println("Message returned:" + review);
    }
}
