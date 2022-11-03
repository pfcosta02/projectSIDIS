package com.example.project.controllers;

import com.example.project.model.VoteDTO;
import com.example.project.usermanagement.model.Role;
import com.example.project.views.ReviewView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import com.example.project.model.VoteDTO;
import com.example.project.model.Review;
import com.example.project.services.ReviewService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Tag(name = "Reviews", description = "Endpoints for managing reviews")
@RestController
@RequestMapping("/api/reviews")

public class    ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService service;


    @Operation(summary = "Gets Approved Reviews for a Product Sorted by data and number of votes")
    @GetMapping(value = "/product/{productId}/date/votes")
    public Iterable<Review> getApprovedReviews(@PathVariable("productId") final Long productId) throws IOException, InterruptedException {

        String url = "http://localhost:8081/api/products/" + productId;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());


        if (response.body().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }

        return service.findApprovedReviews(productId);
    }


    @Operation(summary = "Gets Approved Reviews for a Product Sorted by data")
    @GetMapping(value = "/product/{productId}/date")
    public Iterable<Review> findApprovedReviewsByDate(@PathVariable("productId") final Long productId) throws IOException, InterruptedException {

        String url = "http://localhost:8081/api/products/" + productId;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());


        if (response.body().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }

        return service.findApprovedReviewsByDate(productId);
    }


    @Operation(summary = "Gets Pending Review")
    @GetMapping(value = "/pending")
    @RolesAllowed(Role.MODERATOR)
    public Iterable<Review> getPendingReviews() {
        return service.findAllPending();
    }

    @Operation(summary = "Gets Reviews of a client")
    @GetMapping(value = "/customer/{id}")
    @RolesAllowed(Role.CUSTOMER)
    public Iterable<ReviewView> findMyReviews(@PathVariable("id") final Long customerId,final WebRequest request2) throws IOException, InterruptedException {
        String url = "http://localhost:8080/api/customer/user/" + customerId;

        final String auth = request2.getHeader("Authorization");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", auth)
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found");
        }

        return service.findMyReviews(customerId);
    }

    @Operation(summary = "Find a review by their ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Review> findById(@PathVariable("id") final Long reviewId) {
        final var review = service.findOne(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found"));

        return ResponseEntity.ok().eTag(Long.toString(review.getVersion())).body(review);
    }

    @Operation(summary = "Creates a Review")
    @RolesAllowed(Role.CUSTOMER)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Review> create(@Valid @RequestBody final Review resource) throws IOException, InterruptedException {

        String url = "http://localhost:8081/api/products/" + resource.getProductId();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());


        if (response.body().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }

        final var review = service.create(resource);

        return ResponseEntity.ok().eTag(Long.toString(review.getVersion())).body(review);
    }

    @Operation(summary = "Partially updates an existing review")
    @RolesAllowed(Role.MODERATOR)
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Review> partialUpdate(final WebRequest request,
                                             @PathVariable("id") @Parameter(description = "The id of the review to update") final Long id,
                                             @Valid @RequestBody final Review resource) {
        final String ifMatchValue = request.getHeader("If-Match");
        if (ifMatchValue == null || ifMatchValue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must issue a conditional PATCH using 'if-match'");
        }

        final var review = service.partialUpdate(id, resource, Long.parseLong(ifMatchValue));
        return ResponseEntity.ok().eTag(Long.toString(review.getVersion())).body(review);
    }


    @Operation(summary = "Customer can delete one of his reviews - available only if reviews has no votes")
    @RolesAllowed(Role.CUSTOMER)
    @DeleteMapping(value = "/{reviewId}")
    public ResponseEntity<Review> deleteReview(final WebRequest request, @PathVariable("reviewId") final Long reviewId) {
        final String ifMatchValue = request.getHeader("If-Match");
        if (ifMatchValue == null || ifMatchValue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must issue a conditional DELETE using 'if-match'");
        }
        service.deleteById(reviewId, Long.parseLong(ifMatchValue));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "UpVotes")
    @GetMapping(value = "/upVote/{reviewId}")
    public ResponseEntity<Review> getVotes(@PathVariable("reviewId") final Long reviewId) throws IOException, InterruptedException {

        final var review = service.findOne(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found"));


        String url = "http://localhost:8083/api/votes/" + reviewId;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review has no votes");
        }

        ObjectMapper mapper = new ObjectMapper();

        List<VoteDTO> votes = mapper.readValue(response.body(), new TypeReference<List<VoteDTO>>() {});

        service.getVotes(review, votes);

        return ResponseEntity.ok().eTag(Long.toString(review.getVersion())).body(review);
    }

}
