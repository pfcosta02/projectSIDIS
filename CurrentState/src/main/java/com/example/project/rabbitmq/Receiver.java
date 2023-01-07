package com.example.project.rabbitmq;
import com.example.project.exceptions.MyResourceNotFoundException;
import com.example.project.model.Product;
import com.example.project.model.Review;
import com.example.project.model.Vote;
import com.example.project.repositories.ProductRepository;
import com.example.project.repositories.ReviewRepository;
import com.example.project.repositories.VoteRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class Receiver {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VoteRepository voteRepository;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
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

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void updateReview(Review review) {
        final var review2 = repository.findByUUID(review.getUuid())
                .orElseThrow(() -> new MyResourceNotFoundException("Cannot update an object that does not yet exist"));

        review2.applyPatch(review, 0);

        repository.save(review2);

        System.out.println("Review updated:" + review2);
    }

    @RabbitListener(queues = "#{autoDeleteQueue3.name}")
    public void deleteReview(UUID uuid) {
        repository.deleteByIdIfMatch(uuid,1);

        System.out.println("Review deleted:" + uuid);
    }

    @RabbitListener(queues = "#{autoDeleteQueue4.name}")
    public void consumeMessage(Vote vote) {

        final Vote obj = Vote.newFrom(vote);

        voteRepository.save(obj);

        System.out.println("Vote received:" + vote);
    }

    @RabbitListener(queues = "#{autoDeleteQueue6.name}")
    public void updateVote(UUID uuid) {
        List<Vote> prod = voteRepository.findVotesReviewPending(uuid);

        for (Vote aux: prod) {
            aux.applyPatch(aux.getVersion());

            voteRepository.save(aux);
        }

        System.out.println("Review updated:" + prod.get(0));
    }

    @RabbitListener(queues = "#{autoDeleteQueue5.name}")
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
}
