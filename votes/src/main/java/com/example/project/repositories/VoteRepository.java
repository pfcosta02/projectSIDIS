package com.example.project.repositories;

import com.example.project.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long> {

    @Modifying
    @Query("SELECT f FROM Vote f WHERE f.reviewId = ?1 ")
    Iterable<Vote> findByReview(Long reviewId);

}
