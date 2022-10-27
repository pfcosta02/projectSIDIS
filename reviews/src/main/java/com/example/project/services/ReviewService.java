package com.example.project.services;

import java.util.Optional;

import com.example.project.model.Review;
import com.example.project.views.ReviewView;

public interface ReviewService {

    Iterable<Review> findAll();

    Iterable<Review> findApprovedReviews(Long productId);

    Iterable<Review> findApprovedReviewsByDate(Long productId);

    Iterable<Review> findAllPending();

    Iterable<ReviewView> findMyReviews(Long id);

    Optional<Review> findOne(Long id);

    /**
     * Create a new Review and assign its id.
     *
     * @param resource
     * @return
     */
    Review create(Review resource);

    /**
     * Creates a new Review with the specified id.
     *
     * @param id
     * @param resource
     * @return
     */
    Review create(Long id, Review resource);
    void deleteById(Long id, long parseLong);

    /**
     * Partial updates an existing Foo.
     *
     * @param id
     * @param resource  "patch document"
     * @param parseLong
     * @return
     */
    Review partialUpdate(Long id, Review resource, long parseLong);


}

