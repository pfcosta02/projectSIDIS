package com.example.project.rabbitmq;

import com.example.project.model.*;
import com.example.project.repositories.ProductRepository;
import com.example.project.repositories.ReviewRepository;
import com.example.project.repositories.VoteRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SentToClass {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private VoteRepository voteRepository;

    @RabbitListener(queues = "rpc_receiver")
    public List<ProductDTO> sendProd(){
        List<Product> prod = productRepo.findAllProducts();
        List<ProductDTO> allProductsDto = new ArrayList<>();
        for (Product aux: prod) {
            ProductDTO product = new ProductDTO(aux.getSku(),aux.getName(),aux.getDescription(),aux.getSetOfImages());
            allProductsDto.add(product);
        }
        return allProductsDto ;
    }

    @RabbitListener(queues = "rpc_rev_receiver")
    public List<ReviewDTO> sendRev(){
        Iterable<Review> rev = reviewRepository.findAll();
        List<ReviewDTO> allReviewsDto = new ArrayList<>();
        for (Review aux: rev) {
            ReviewDTO review = new ReviewDTO(aux.getReviewId(),aux.getUuid(),aux.getText(),aux.getRating(),aux.getUpVote(),aux.getDownVote(),aux.getDataTime(),aux.getStatus(),aux.getProductSku(),aux.getCustomerId(),aux.getFunnyFact(),aux.getVersion());
            allReviewsDto.add(review);
        }
        return allReviewsDto ;
    }

    @RabbitListener(queues = "rpc_vote_receiver")
    public List<VoteDTO> sendVote(){
        Iterable<Vote> vote = voteRepository.findAll();
        List<VoteDTO> allVoteDto = new ArrayList<>();
        for (Vote aux: vote) {
            VoteDTO voteDTO = new VoteDTO(aux.getId(),aux.getVote(),aux.getUuid(),aux.getCustomerId(),aux.getVersion());
            allVoteDto.add(voteDTO);
        }
        return allVoteDto ;
    }
}
