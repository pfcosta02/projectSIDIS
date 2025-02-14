package com.example.project.rabbitmq;
import com.example.project.exceptions.MyResourceNotFoundException;
import com.example.project.model.Product;
import com.example.project.model.Review;
import com.example.project.repositories.ProductRepository;
import com.example.project.repositories.ReviewRepository;
import com.example.project.services.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Optional;
import java.util.UUID;

@Component
public class Receiver {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository repository;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void consumeMessage(Product product) {

        Optional<Product> optional = productRepository.findBySku(product.getSku());

        if(optional.isPresent()) {
            System.out.println("Product Duplicated");
            return;
        }

        final Product obj = Product.newFrom(product);

        productRepository.save(obj);
        System.out.println("Product received:" + product);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void consumeMessageCreate(Review review) {

        Optional<Review> optional = repository.findByUUID(review.getUuid());

        if(optional.isPresent()) {
            System.out.println("Review Duplicated");
            return;
        }

        final Review obj = Review.newFrom(review);

        repository.save(obj);

        System.out.println("Review received:" + review);

    }

    @RabbitListener(queues = "#{autoDeleteQueue3.name}")
    public void updateReview(Review review) {
        final var review2 = repository.findByUUID(review.getUuid())
                .orElseThrow(() -> new MyResourceNotFoundException("Cannot update an object that does not yet exist"));

        review2.applyPatch(review, review2.getVersion());

        repository.save(review2);

        System.out.println("Review updated:" + review2);
    }

    @RabbitListener(queues = "#{autoDeleteQueue4.name}")
    public void deleteReview(UUID uuid) {
        repository.deleteByIdIfMatch(uuid,1);

        System.out.println("Review deleted:" + uuid);
    }
}
