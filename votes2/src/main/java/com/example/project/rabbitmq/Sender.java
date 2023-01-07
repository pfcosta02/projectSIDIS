package com.example.project.rabbitmq;

import com.example.project.model.Review;
import com.example.project.model.Vote;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Sender {

    @Autowired
    private AmqpTemplate template;

    @Autowired
    private FanoutExchange fanout;

    public void send(String exchange, Vote vote) {
        template.convertAndSend(exchange, "", vote);
        System.out.println(" [x] Sent '" + vote + "'");
    }

    public void send(String exchange, Review review) {
        template.convertAndSend(exchange, "", review);
        System.out.println(" [x] Sent '" + review + "'");
    }

    public void update(String exchange, Vote vote) {
        template.convertAndSend(exchange, "", vote);
        System.out.println(" [x] Sent '" + vote + "'");
    }

}
