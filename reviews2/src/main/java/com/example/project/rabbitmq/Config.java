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

    String updateReview = "update_reviews_One";

    String updateReviewGetsOne = "update_reviews_Get_One";

    String updateReviewGetsTwo = "update_reviews_Get_Two";

    String exchangeUpdate = "update_reviews_two_sidis";

    String deleteQueue = "delete_reviews_One";

    String deleteGetOneQueue = "delete_reviews_Get_One";

    String deleteGetTwoQueue = "delete_reviews_Get_Two";

    String exchangeDelete = "delete_reviews_two_sidis";

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

    // update
    @Bean
    Queue updateQueue() {
        return new Queue(updateReview, false);
    }

    @Bean
    Queue updateGetOne() {
        return new Queue(updateReviewGetsOne, false);
    }

    @Bean
    Queue updateGetTwo() {
        return new Queue(updateReviewGetsTwo, false);
    }

    @Bean
    FanoutExchange updateExchange() {
        return new FanoutExchange(exchangeUpdate);
    }

    @Bean
    Binding updateReviewsBinding(Queue updateQueue, FanoutExchange updateExchange) {
        return BindingBuilder.bind(updateQueue).to(updateExchange);
    }

    @Bean
    Binding updateReviewsGetOneBinding(Queue updateGetOne, FanoutExchange updateExchange) {
        return BindingBuilder.bind(updateGetOne).to(updateExchange);
    }

    @Bean
    Binding updateReviewsGetTwoBinding(Queue updateGetTwo, FanoutExchange updateExchange) {
        return BindingBuilder.bind(updateGetTwo).to(updateExchange);
    }

    // delete
    @Bean
    Queue deleteQueue() {
        return new Queue(deleteQueue, false);
    }

    @Bean
    Queue deleteGetOne() {
        return new Queue(deleteGetOneQueue, false);
    }

    @Bean
    Queue deleteGetTwo() {
        return new Queue(deleteGetTwoQueue, false);
    }

    @Bean
    FanoutExchange deleteExchange() {
        return new FanoutExchange(exchangeDelete);
    }

    @Bean
    Binding deleteReviewsBinding(Queue deleteQueue, FanoutExchange deleteExchange) {
        return BindingBuilder.bind(deleteQueue).to(deleteExchange);
    }

    @Bean
    Binding deleteReviewsGetOneBinding(Queue deleteGetOne, FanoutExchange deleteExchange) {
        return BindingBuilder.bind(deleteGetOne).to(deleteExchange);
    }

    @Bean
    Binding deleteReviewsGetTwoBinding(Queue deleteGetTwo, FanoutExchange deleteExchange) {
        return BindingBuilder.bind(deleteGetTwo).to(deleteExchange);
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
