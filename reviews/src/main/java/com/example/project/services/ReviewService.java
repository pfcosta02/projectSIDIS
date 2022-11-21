package com.example.project.services;

import java.util.List;
import java.util.Optional;

import com.example.project.model.Review;
import com.example.project.model.ReviewDTO;
import com.example.project.model.VoteDTO;
import com.example.project.views.ReviewView;
import org.springframework.web.context.request.WebRequest;

public interface ReviewService {

    List<ReviewDTO> findApprovedReviews(String productSku);
    List<ReviewDTO> findApprovedReviewsAll(String productSku);

    List<ReviewDTO> findApprovedReviewsByDate(String productSku);
    List<ReviewDTO> findApprovedReviewsByDateAll(String productSku);

    List<ReviewDTO> findAllPending(WebRequest request2);
    List<ReviewDTO> findAllPendingAll();

    List<ReviewDTO> findMyReviews(Long id, WebRequest request);
    List<ReviewDTO> findMyReviewsAll(Long id, WebRequest request);

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

    void getVotes(Review review, List<VoteDTO> votes);
}

