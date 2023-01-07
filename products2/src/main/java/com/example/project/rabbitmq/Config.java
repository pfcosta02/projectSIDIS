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

    @Bean
    public Queue autoDeleteQueue1() {
        return new AnonymousQueue();
    }

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("product_fanout");
    }

    @Bean
    public Queue autoDeleteQueue2() {
        return new AnonymousQueue();
    }

    @Bean
    public FanoutExchange reviewFanout() {
        return new FanoutExchange("review_create_fanout");
    }

    @Bean
    public Binding binding1(FanoutExchange fanout,
                            Queue autoDeleteQueue1) {
        return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
    }

    @Bean
    public Binding binding2(FanoutExchange reviewFanout,
                            Queue autoDeleteQueue2) {
        return BindingBuilder.bind(autoDeleteQueue2).to(reviewFanout);
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


    // RPC
    @Bean
    public Queue queueReceiver(){
        return new Queue("rpc_receiver");
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("rpc");
    }

    @Bean
    public Binding binding3(DirectExchange directExchange, Queue queueReceiver){
        return BindingBuilder.bind(queueReceiver).to(directExchange).with("key");
    }

    @Bean
    public Queue queueReceiver2(){
        return new Queue("rpc_rev_receiver");
    }

    @Bean
    public DirectExchange directExchange2(){
        return new DirectExchange("rpc_rev");
    }

    @Bean
    public Binding binding4(DirectExchange directExchange2, Queue queueReceiver2){
        return BindingBuilder.bind(queueReceiver2).to(directExchange2).with("key");
    }
}
