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
        return new FanoutExchange("vote_fanout");
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
    public Queue autoDeleteQueue3() {
        return new AnonymousQueue();
    }

    @Bean
    public FanoutExchange updateVoteFanout() {
        return new FanoutExchange("vote_update_fanout");
    }

    @Bean
    public Queue autoDeleteQueue4() {
        return new AnonymousQueue();
    }

    @Bean
    public FanoutExchange updateReviewFanout() {
        return new FanoutExchange("review_update_fanout");
    }

    @Bean
    public Queue autoDeleteQueue5() {
        return new AnonymousQueue();
    }

    @Bean
    public FanoutExchange deleteReviewFanout() {
        return new FanoutExchange("review_delete_fanout");
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
    public Binding binding3(FanoutExchange updateVoteFanout,
                            Queue autoDeleteQueue3) {
        return BindingBuilder.bind(autoDeleteQueue3).to(updateVoteFanout);
    }

    @Bean
    public Binding binding4(FanoutExchange updateReviewFanout,
                            Queue autoDeleteQueue4) {
        return BindingBuilder.bind(autoDeleteQueue4).to(updateReviewFanout);
    }

    @Bean
    public Binding binding5(FanoutExchange deleteReviewFanout,
                            Queue autoDeleteQueue5) {
        return BindingBuilder.bind(autoDeleteQueue5).to(deleteReviewFanout);
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


    @Bean
    public Queue queueReceiver(){
        return new Queue("rpc_rev_receiver");
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("rpc_rev");
    }

    @Bean
    public Binding binding6(DirectExchange directExchange, Queue queueReceiver){
        return BindingBuilder.bind(queueReceiver).to(directExchange).with("key");
    }


    @Bean
    public Queue queueReceiver2(){
        return new Queue("rpc_vote_receiver");
    }

    @Bean
    public DirectExchange directExchange2(){
        return new DirectExchange("rpc_vote");
    }

    @Bean
    public Binding binding7(DirectExchange directExchange2, Queue queueReceiver2){
        return BindingBuilder.bind(queueReceiver2).to(directExchange2).with("key");
    }
}
