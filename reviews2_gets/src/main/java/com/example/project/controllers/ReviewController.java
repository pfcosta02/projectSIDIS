package com.example.project.controllers;

import com.example.project.model.ReviewDTO;
import com.example.project.model.VoteDTO;
import com.example.project.usermanagement.model.Role;
import com.example.project.views.ReviewView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
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
import java.util.UUID;

@Tag(name = "Reviews", description = "Endpoints for managing reviews")
@RestController
@RequestMapping("/api/reviews")

public class    ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService service;

    @Autowired
    private JwtDecoder jwtDecoder;


    @Operation(summary = "Gets Approved Reviews for a Product Sorted by data and number of votes")
    @GetMapping(value = "/product/{productSku}/date/votes")
    public List<ReviewDTO> getApprovedReviews(@PathVariable("productSku") final String productSku) {

        return service.findApprovedReviews(productSku);
    }

    @Operation(summary = "Gets Approved Reviews for a Product Sorted by data and number of votes")
    @GetMapping(value = "/product/{productSku}/date/votes/anotherApp")
    public List<ReviewDTO> getApprovedReviewsAnotherApp(@PathVariable("productSku") final String productSku) {

        return service.findApprovedReviewsAll(productSku);
    }


    @Operation(summary = "Gets Approved Reviews for a Product Sorted by data")
    @GetMapping(value = "/product/{productSku}/date")
    public List<ReviewDTO> findApprovedReviewsByDate(@PathVariable("productSku") final String productSku) {

        return service.findApprovedReviewsByDate(productSku);
    }

    @Operation(summary = "Gets Approved Reviews for a Product Sorted by data")
    @GetMapping(value = "/product/{productSku}/date/anotherApp")
    public List<ReviewDTO> findApprovedReviewsByDateAnotherApp(@PathVariable("productSku") final String productSku) {

        return service.findApprovedReviewsByDateAll(productSku);
    }


    @Operation(summary = "Gets Pending Review")
    @GetMapping(value = "/pending")
    @RolesAllowed(Role.MODERATOR)
    public List<ReviewDTO> getPendingReviews(final WebRequest request) {
        return service.findAllPending(request);
    }

    @Operation(summary = "Gets Pending Review")
    @GetMapping(value = "/pending/anotherApp")
    @RolesAllowed(Role.MODERATOR)
    public List<ReviewDTO> getPendingReviewsAnotherApp() {
        return service.findAllPendingAll();
    }

    @Operation(summary = "Gets Reviews of a client")
    @GetMapping(value = "/customer/{id}")
    @RolesAllowed(Role.CUSTOMER)
    public List<ReviewDTO> findMyReviews(@PathVariable("id") final Long customerId,final WebRequest request) {

        return service.findMyReviews(customerId,request);
    }

    @Operation(summary = "Gets Reviews of a client")
    @GetMapping(value = "/customer/{id}/anotherApp")
    @RolesAllowed(Role.CUSTOMER)
    public List<ReviewDTO> findMyReviewsAnotherApp(@PathVariable("id") final Long customerId,final WebRequest request) {

        return service.findMyReviewsAll(customerId,request);
    }

    @Operation(summary = "Find a review by their ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Review> findById(@PathVariable("id") final Long reviewId) {
        final var review = service.findOne(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found"));

        return ResponseEntity.ok().eTag(Long.toString(review.getVersion())).body(review);
    }


    @Operation(summary = "Find a review by their UUID")
    @GetMapping(value = "/{uuid}")
    public ResponseEntity<ReviewDTO> findByUUID(@PathVariable("uuid") final UUID uuid) {
        final var review = service.findByUUID(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found"));

        return ResponseEntity.ok().body(review);
    }


    @Operation(summary = "Find a review by their UUID")
    @GetMapping(value = "/{uuid}/anotherApp")
    public ResponseEntity<ReviewDTO> findByUUIDAnotherApp(@PathVariable("uuid") final UUID uuid) {
        final var review = service.findByUUIDAll(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found"));

        return ResponseEntity.ok().body(review);
    }

    @Operation(summary = "UpVotes")
    @GetMapping(value = "/votes/{reviewId}")
    public ResponseEntity<Review> getVotes(@PathVariable("reviewId") final Long reviewId) {

        final var review = service.findOne(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found"));
        try{
            String url = "http://localhost:8095/api/votes/" + reviewId;

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

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().eTag(Long.toString(review.getVersion())).body(review);
    }

}
