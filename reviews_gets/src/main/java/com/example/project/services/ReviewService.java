package com.example.project.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.project.model.Review;
import com.example.project.model.ReviewDTO;
import com.example.project.model.VoteDTO;
import com.example.project.views.ReviewView;
import org.springframework.web.context.request.WebRequest;

public interface ReviewService {

    List<ReviewDTO> findApprovedReviews(String productSku);

    List<ReviewDTO> findApprovedReviewsByDate(String productSku);

    List<ReviewDTO> findAllPending(WebRequest request2);

    List<ReviewDTO> findMyReviews(Long id, WebRequest request);

    Optional<Review> findOne(Long id);

    Optional<Review> findByUUID(UUID uuid);

    void getVotes(Review review);
}

