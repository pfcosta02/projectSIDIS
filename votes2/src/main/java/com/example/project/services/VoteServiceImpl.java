package com.example.project.services;

import com.example.project.exceptions.MyResourceNotFoundException;
import com.example.project.model.Review;
import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;
import com.example.project.rabbitmq.Sender;
import com.example.project.repositories.ReviewRepository;
import com.example.project.repositories.VoteRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository repository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private Sender sender;

    public String exchange = "vote_fanout";

    public String exchangeUpdate = "vote_update_fanout";

    public String exchangeReview = "review_create_fanout";

    @Override
    public Vote create(final Vote resource) {

        final var optionalReview = reviewRepository.findByUUID(resource.getUuid());

        if(optionalReview.isEmpty()) {
            Review reviewResourse = new Review(resource.getUuid(),resource.getText(), resource.getRating(), resource.getProductSku(), resource.getCustomerId());

            final Review review = Review.newFrom(reviewResourse);

            reviewRepository.save(review);

            sender.send(exchangeReview, review);

            resource.setStatus("pending");
        }

        final Vote obj = Vote.newFrom(resource);
        sender.send(exchange, obj);

        return repository.save(obj);
    }

    @Override
    public Vote create(final Long id, final Vote resource) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Vote partialUpdate(final UUID uuid, final Vote resource, final long desiredVersion) {
        // first let's check if the object exists so we don't create a new object with
        // save

        List<Vote> prod = repository.findVotesReviewPending(uuid);

        for (Vote aux: prod) {
            aux.applyPatch(desiredVersion);
            sender.update(exchangeUpdate, aux);
            repository.save(aux);
        }

        // in the meantime some other user might have changed this object on the
        // database, so concurrency control will still be applied when we try to save
        // this updated object
        return repository.save(prod.get(0));
    }
}
