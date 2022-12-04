package com.example.project.repositories;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Transactional
    @Modifying
    @Query("DELETE FROM Review f WHERE f.uuid = ?1 AND f.version = ?2 AND ABS(f.upVote + f.downVote) = 0" )
    void deleteByIdIfMatch(UUID uuid, long desiredVersion);

    @Modifying
    @Query("SELECT f FROM Review f WHERE f.status = 'Approved' AND f.productSku = ?1 ORDER BY ABS(f.downVote + f.upVote) DESC, f.dataTime DESC")
    List<Review> findApprovedReviews(String productSku);

    @Modifying
    @Query("SELECT f FROM Review f WHERE f.status = 'Approved' AND f.productSku = ?1 ORDER BY f.dataTime DESC")
    List<Review> findApprovedReviewsByDate(String productSku);

    @Modifying
    @Query("SELECT f FROM Review f WHERE f.status = 'Pending'")
    List<Review> findAllPending();

    @Query("SELECT f FROM Review f WHERE f.customerId = ?1")
    List<Review> findMyReviews(Long customerId);

    @Query("SELECT f FROM Review f WHERE f.uuid = ?1")
    Optional<Review> findByUUID(UUID uuid);
}
