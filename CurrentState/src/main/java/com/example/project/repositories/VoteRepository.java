package com.example.project.repositories;

import com.example.project.model.Vote;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoteRepository extends CrudRepository<Vote, Long> {

    @Modifying
    @Query("SELECT f FROM Vote f WHERE f.uuid = ?1 ")
    List<Vote> findVotesReview(Long reviewId);

}
