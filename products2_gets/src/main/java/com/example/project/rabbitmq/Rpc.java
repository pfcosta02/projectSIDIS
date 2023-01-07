package com.example.project.rabbitmq;

import com.example.project.model.Product;
import com.example.project.model.ProductDTO;
import com.example.project.model.Review;
import com.example.project.model.ReviewDTO;
import com.example.project.repositories.ProductRepository;
import com.example.project.repositories.ReviewRepository;
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
    private ProductRepository prodRepo;

    @Autowired
    private ReviewRepository revRepo;

    public void helper(){
        List<Product> finalProductList = new ArrayList<>();
        List<ProductDTO> prod = (List<ProductDTO>) amqpTemplate.convertSendAndReceive("rpc", "key", "");

        if (prod.isEmpty()){ return; }

        for (ProductDTO aux:prod){
            finalProductList.add(new Product(aux.sku,aux.description, aux.name));
        }
        for (Product auxAdd: finalProductList){
            prodRepo.save(auxAdd);
        }
    }

    public void helper2() {
        List<Review> finalReviewList = new ArrayList<>();
        List<ReviewDTO> rev = (List<ReviewDTO>) amqpTemplate.convertSendAndReceive("rpc_rev", "key", "");

        if (rev.isEmpty()){ return; }

        for (ReviewDTO aux:rev){
            finalReviewList.add(new Review(aux.reviewId,aux.uuid,aux.text,aux.rating,aux.upVote,aux.downVote,aux.dataTime,aux.status,aux.productSku,aux.customerId,aux.funnyFact,aux.version));
        }
        for (Review auxAdd: finalReviewList){
            revRepo.save(auxAdd);
        }

    }
}
