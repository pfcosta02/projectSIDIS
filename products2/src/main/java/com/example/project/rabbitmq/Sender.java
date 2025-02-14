package com.example.project.rabbitmq;

import com.example.project.model.Product;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private AmqpTemplate template;

    @Autowired
    private FanoutExchange fanout;

    public void send(String exchange, Product product) {
        template.convertAndSend(exchange, "", product);
        System.out.println(" [x] Sent '" + product + "'");
    }

}
