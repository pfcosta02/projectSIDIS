package com.example.project.repositories;
import java.util.List;
import java.util.Optional;

import com.example.project.views.ReviewView;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.project.model.Review;

import javax.transaction.Transactional;

public interface ReviewRepository extends CrudRepository<Review, Long> {
   /*
    @Modifying
    @Query("SELECT f FROM Review f ORDER BY f.dataTime")

    */
    Optional<Review> findById(Long reviewId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Review f WHERE f.reviewId = ?1 AND f.version = ?2 AND ABS(f.upVote + f.downVote) = 0" )
    void deleteByIdIfMatch(Long reviewId, long desiredVersion);

    Iterable<Review> findAll();

    @Modifying
    @Query("SELECT f FROM Review f WHERE f.status = 'Approved' AND f.productId = ?1 ORDER BY ABS(f.downVote + f.upVote) DESC, f.dataTime DESC")
    Iterable<Review> findApprovedReviews(Long productId);

    @Modifying
    @Query("SELECT f FROM Review f WHERE f.status = 'Approved' AND f.productId = ?1 ORDER BY f.dataTime DESC")
    Iterable<Review> findApprovedReviewsByDate(Long productId);

    @Modifying
    @Query("SELECT f FROM Review f WHERE f.status = 'Pending'")
    Iterable<Review> findAllPending();

    @Query("SELECT f.text AS text, f.status AS status FROM Review f WHERE f.customerId = ?1")
    List<ReviewView> findMyReviews(Long customerId);
}
