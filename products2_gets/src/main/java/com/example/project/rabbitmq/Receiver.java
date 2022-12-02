package com.example.project.rabbitmq;
import com.example.project.model.Product;
import com.example.project.repositories.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    private ProductRepository repository;

    @RabbitListener(queues = "products_Get_Two")
    public void consumeMessage(Product product) {
        final Product obj = Product.newFrom(product);
        repository.save(obj);
        System.out.println("Message returned:" + product);
    }
}
