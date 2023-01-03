package com.example.project.rabbitmq;

import com.example.project.model.Product;
import com.example.project.model.ProductDTO;
import com.example.project.services.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SentToClass {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "rpc_receiver")
    public List<ProductDTO> send(){
        List<ProductDTO> prod = productService.findAll();
        return prod;
    }
}
