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
import org.springframework.amqp.core.AmqpTemplate;
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

    @Autowired
    private AmqpTemplate amqpTemplate;

    public String exchange = "review_create_fanout";

    public String exchangeDelete = "review_delete_fanout";

    public String exchangeUpdate = "review_update_fanout";

    @Operation(summary = "Creates a Review")
    @RolesAllowed(Role.CUSTOMER)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Review> create(@Valid @RequestBody final Review resource,final WebRequest request2)  {

        final String auth = request2.getHeader("Authorization");

        String newToken = auth.replace("Bearer ", "");
        Jwt decodedToken = this.jwtDecoder.decode(newToken);
        String subject = (String) decodedToken.getClaims().get("sub");
        Long userId = Long.valueOf(subject.split(",")[0]);

        resource.setCustomerId(userId);

        final var review = service.create(resource);
        amqpTemplate.convertAndSend(exchange, "", review);
        return ResponseEntity.ok().eTag(Long.toString(review.getVersion())).body(review);
    }

    @Operation(summary = "Partially updates an existing review")
    @RolesAllowed(Role.MODERATOR)
    @PatchMapping(value = "/{uuid}")
    public ResponseEntity<Review> partialUpdate(final WebRequest request,
                                                @PathVariable("uuid") @Parameter(description = "The uuid of the review to update") final UUID uuid,
                                                @Valid @RequestBody final Review resource) {
        final String ifMatchValue = request.getHeader("If-Match");
        if (ifMatchValue == null || ifMatchValue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must issue a conditional PATCH using 'if-match'");
        }

        final var review = service.partialUpdate(uuid, resource, Long.parseLong(ifMatchValue));
        amqpTemplate.convertAndSend(exchangeUpdate, "", review);
        return ResponseEntity.ok().eTag(Long.toString(review.getVersion())).body(review);
    }

    @Operation(summary = "Customer can delete one of his reviews - available only if reviews has no votes")
    @RolesAllowed(Role.CUSTOMER)
    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<Review> deleteReview(final WebRequest request, @PathVariable("uuid") final UUID uuid) {
        final String ifMatchValue = request.getHeader("If-Match");
        if (ifMatchValue == null || ifMatchValue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must issue a conditional DELETE using 'if-match'");
        }
        service.deleteById(uuid, Long.parseLong(ifMatchValue));
        amqpTemplate.convertAndSend(exchangeDelete, "", uuid);
        return ResponseEntity.noContent().build();
    }

}
