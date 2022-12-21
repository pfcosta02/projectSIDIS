package com.example.project.services;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.project.exceptions.MyResourceNotFoundException;
import com.example.project.model.Product;
import com.example.project.model.ReviewDTO;
import com.example.project.model.VoteDTO;
import com.example.project.rabbitmq.Sender;
import com.example.project.repositories.ProductRepository;
import com.example.project.views.ReviewView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.project.model.Review;
import com.example.project.repositories.ReviewRepository;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Sender sender;

    public String exchange = "review_create_fanout";

    public String exchangeDelete = "review_delete_fanout";

    public String exchangeUpdate = "review_update_fanout";

    @Override
    public Review create(final Review resource) {
        // construct a new object based on data received by the service to ensure domain
        // invariants are met

        Optional<Product> product = productRepository.findBySku(resource.getProductSku());

        if(product.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }

        final Review obj = Review.newFrom(resource);

        sender.send(exchange, obj);

        return repository.save(obj);
    }

    @Override
    public Review create(final Long id, final Review resource) {
        // Foo's id is automatically assigned by the service, so we do not support
        // creation of objects where the client sets the id
        throw new UnsupportedOperationException();
    }

    @Override
    public Review partialUpdate(final UUID uuid, final Review resource, final long desiredVersion) {
        // first let's check if the object exists so we don't create a new object with
        // save
        final var review = repository.findByUUID(uuid)
                .orElseThrow(() -> new MyResourceNotFoundException("Cannot update an object that does not yet exist"));

        // since we got the object from the database we can check the version in memory
        // and apply the patch
        review.applyPatch(resource, desiredVersion);

        sender.update(exchangeUpdate, review);

        // in the meantime some other user might have changed this object on the
        // database, so concurrency control will still be applied when we try to save
        // this updated object
        return repository.save(review);
    }

    @Override
    public void deleteById(final UUID uuid, final long desiredVersion) {
        repository.deleteByIdIfMatch(uuid, desiredVersion);
        sender.delete(exchangeDelete, uuid);
    }
}
