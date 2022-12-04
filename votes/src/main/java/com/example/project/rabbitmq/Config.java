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
    String voteQueue = "vote_Two";

    String voteGetOneQueue = "vote_Get_One";

    String voteGetTwoQueue = "vote_Get_Two";

    String exchange = "vote_one_sidis";

    @Bean
    Queue voteQueue() {
        return new Queue(voteQueue, false);
    }

    @Bean
    Queue vGetOne() {
        return new Queue(voteGetOneQueue, false);
    }

    @Bean
    Queue vGetTwo() {
        return new Queue(voteGetTwoQueue, false);
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(exchange);
    }

    @Bean
    Binding voteBinding(Queue voteQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(voteQueue).to(exchange);
    }

    @Bean
    Binding voteGetOneBinding(Queue vGetOne, FanoutExchange exchange) {
        return BindingBuilder.bind(vGetOne).to(exchange);
    }

    @Bean
    Binding voteGetTwoBinding(Queue vGetTwo, FanoutExchange exchange) {
        return BindingBuilder.bind(vGetTwo).to(exchange);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
