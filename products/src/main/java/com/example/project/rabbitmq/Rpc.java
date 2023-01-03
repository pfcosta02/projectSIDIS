package com.example.project.rabbitmq;

import com.example.project.model.Product;
import com.example.project.model.ProductDTO;
import com.example.project.services.ProductService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Rpc {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ProductService service;

    public void helper(){
        List<Product> finalProductList = new ArrayList<>();
        List<ProductDTO> prod = (List<ProductDTO>) amqpTemplate.convertSendAndReceive("rpc", "key", "");
        if (prod.isEmpty()){
            return;
        }
        for (ProductDTO aux:prod){
            finalProductList.add(new Product(aux.sku,aux.description, aux.name));
        }
        for (Product auxAdd: finalProductList){
            service.create(auxAdd);
        }
    }
}
