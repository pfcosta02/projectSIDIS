package com.example.project.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {
    String reviewsQueue = "reviews_One";

    String reviewsGetOneQueue = "reviews_Get_One";

    String reviewsGetTwoQueue = "reviews_Get_Two";

    String exchange = "reviews_two_sidis";

    @Bean
    Queue reviewsQueue() {
        return new Queue(reviewsQueue, false);
    }

    @Bean
    Queue rGetOne() {
        return new Queue(reviewsGetOneQueue, false);
    }

    @Bean
    Queue rGetTwo() {
        return new Queue(reviewsGetTwoQueue, false);
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(exchange);
    }

    @Bean
    Binding reviewsBinding(Queue reviewsQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(reviewsQueue).to(exchange);
    }

    @Bean
    Binding reviewsGetOneBinding(Queue rGetOne, FanoutExchange exchange) {
        return BindingBuilder.bind(rGetOne).to(exchange);
    }

    @Bean
    Binding reviewsGetTwoBinding(Queue rGetTwo, FanoutExchange exchange) {
        return BindingBuilder.bind(rGetTwo).to(exchange);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setDefaultReceiveQueue("products_Two");
        rabbitTemplate.setMessageConverter(messageConverter());
        rabbitTemplate.setReplyAddress("products_Two");
        rabbitTemplate.setUseDirectReplyToContainer(false);
        return rabbitTemplate;
    }
}
