package com.example.project.rabbitmq;
import com.example.project.model.Product;
import com.example.project.repositories.ProductRepository;
import com.example.project.services.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
public class Receiver {

    @Autowired
    private ProductRepository repository;

    @RabbitListener(queues = "products_Get_One")
    public void consumeMessage(Product product) {
        final Product obj = Product.newFrom(product);
        repository.save(obj);
        System.out.println("Message returned:" + product);
    }
}
