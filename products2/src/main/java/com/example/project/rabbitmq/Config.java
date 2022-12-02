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

    String productsQueue = "products_One";

    String productsGetOneQueue = "products_Get_One";

    String productsGetTwoQueue = "products_Get_Two";

    String exchange = "products_two_sidis";

    @Bean
    Queue productQueue() {
        return new Queue(productsQueue, false);
    }

    @Bean
    Queue pGetOne() {
        return new Queue(productsGetOneQueue, false);
    }

    @Bean
    Queue pGetTwo() {
        return new Queue(productsGetTwoQueue, false);
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(exchange);
    }

    @Bean
    Binding productBinding(Queue productQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(productQueue).to(exchange);
    }

    @Bean
    Binding productGetOneBinding(Queue pGetOne, FanoutExchange exchange) {
        return BindingBuilder.bind(pGetOne).to(exchange);
    }

    @Bean
    Binding productGetTwoBinding(Queue pGetTwo, FanoutExchange exchange) {
        return BindingBuilder.bind(pGetTwo).to(exchange);
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