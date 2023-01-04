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
        return new FanoutExchange("review_create_fanout");
    }

    @Bean
    public Queue autoDeleteQueue2() {
        return new AnonymousQueue();
    }

    @Bean
    public FanoutExchange updateFanout() {
        return new FanoutExchange("review_update_fanout");
    }

    @Bean
    public Queue autoDeleteQueue3() {
        return new AnonymousQueue();
    }

    @Bean
    public FanoutExchange deleteFanout() {
        return new FanoutExchange("review_delete_fanout");
    }

    @Bean
    public Queue autoDeleteQueue4() {
        return new AnonymousQueue();
    }

    @Bean
    public FanoutExchange voteFanout() {
        return new FanoutExchange("vote_fanout");
    }

    @Bean
    public Queue autoDeleteQueue5() {
        return new AnonymousQueue();
    }

    @Bean
    public FanoutExchange productFanout() {
        return new FanoutExchange("product_fanout");
    }

    @Bean
    public Binding binding1(FanoutExchange fanout,
                            Queue autoDeleteQueue1) {
        return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
    }

    @Bean
    public Binding binding2(FanoutExchange updateFanout,
                            Queue autoDeleteQueue2) {
        return BindingBuilder.bind(autoDeleteQueue2).to(updateFanout);
    }

    @Bean
    public Binding binding3(FanoutExchange deleteFanout,
                            Queue autoDeleteQueue3) {
        return BindingBuilder.bind(autoDeleteQueue3).to(deleteFanout);
    }

    @Bean
    public Binding binding4(FanoutExchange voteFanout,
                            Queue autoDeleteQueue4) {
        return BindingBuilder.bind(autoDeleteQueue4).to(voteFanout);
    }

    @Bean
    public Binding binding5(FanoutExchange productFanout,
                            Queue autoDeleteQueue5) {
        return BindingBuilder.bind(autoDeleteQueue5).to(productFanout);
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
        return new Queue("rpc_receiver");
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("rpc");
    }

    @Bean
    public Binding binding(DirectExchange directExchange, Queue queueReceiver){
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
    public Binding binding2(DirectExchange directExchange2, Queue queueReceiver2){
        return BindingBuilder.bind(queueReceiver2).to(directExchange2).with("key");
    }



    @Bean
    public Queue queueReceiver3(){
        return new Queue("rpc_vote_receiver");
    }

    @Bean
    public DirectExchange directExchange3(){
        return new DirectExchange("rpc_vote");
    }

    @Bean
    public Binding binding3(DirectExchange directExchange3, Queue queueReceiver3){
        return BindingBuilder.bind(queueReceiver3).to(directExchange3).with("key");
    }
}
