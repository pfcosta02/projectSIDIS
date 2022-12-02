package com.example.project.rabbitmq;
import com.example.project.model.Product;
import com.example.project.services.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
public class Receiver {

    @Autowired
    private ProductService service;

    @RabbitListener(queues = "products_Two")
    public void consumeMessage(Product product) {
        final var product2 = service.create(product);
        System.out.println("Message returned:" + product);
    }
}
