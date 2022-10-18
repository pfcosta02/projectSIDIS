package com.example.project.controllers;

import com.example.project.views.ReviewView;
import com.example.project.services.ProductService;
import com.example.project.usermanagement.model.Role;
import com.example.project.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import com.example.project.model.Review;
import com.example.project.services.ReviewService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Tag(name = "Reviews", description = "Endpoints for managing reviews")
@RestController
@RequestMapping("/api/reviews")
public class    ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService service;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Gets Approved Reviews for a Product Sorted by data and number of votes")
    @GetMapping(value = "/product/{productId}/date/votes")
    public Iterable<Review> getApprovedReviews(@PathVariable("productId") final Long productId) {
        final var product = productService.findOne(productId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

        return service.findApprovedReviews(productId);
    }

    @Operation(summary = "Gets Approved Reviews for a Product Sorted by data")
    @GetMapping(value = "/product/{productId}/date")
    public Iterable<Review> findApprovedReviewsByDate(@PathVariable("productId") final Long productId) {
        final var product = productService.findOne(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

        return service.findApprovedReviewsByDate(productId);
    }

    @Operation(summary = "Gets Pending Review")
    @RolesAllowed(Role.MODERATOR)
    @GetMapping(value = "/pending")
    public Iterable<Review> getPendingReviews() {
        return service.findAllPending();
    }

    @Operation(summary = "Gets Reviews of a client")
    @RolesAllowed(Role.CUSTOMER)
    @GetMapping(value = "/customer/{id}")
    public Iterable<ReviewView> findMyReviews(@PathVariable("id") final Long customerId) {
        final var customer = userService.getUser(customerId);

        if(customer.getId() == null) {
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not Found");
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
    public ResponseEntity<Review> create(@Valid @RequestBody final Review resource) {

        final var product = productService.findOne(resource.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

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

    @Operation(summary = "Vote a like in a review")
    @RolesAllowed(Role.CUSTOMER)
    @PatchMapping(value = "/{id}/upvote")
    public ResponseEntity<Review> upVote(final WebRequest request,
                                                @PathVariable("id") @Parameter(description = "The id of the review to update") final Long id,
                                                @Valid @RequestBody final Review resource) {

        final var review = service.upVote(id, resource);
        return ResponseEntity.ok().eTag(Long.toString(review.getVersion())).body(review);
    }

    @Operation(summary = "Vote a dislike in a review")
    @RolesAllowed(Role.CUSTOMER)
    @PatchMapping(value = "/{id}/downvote")
    public ResponseEntity<Review> downVote(final WebRequest request,
                                         @PathVariable("id") @Parameter(description = "The id of the review to update") final Long id,
                                         @Valid @RequestBody final Review resource) {


        final var review = service.downVote(id, resource);
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
}
