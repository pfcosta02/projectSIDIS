package com.example.project.repositories;

import com.example.project.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface VoteRepository extends CrudRepository<Vote, Long> {

    @Modifying
    @Query("SELECT f FROM Vote f WHERE f.uuid = ?1 ")
    List<Vote> findVotesReview(UUID uuid);

}
