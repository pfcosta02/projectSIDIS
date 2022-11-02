package com.example.project.services;

import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;

import java.util.List;

public interface VoteService {

    Vote create(Vote resource);
    Vote create(Long id, Vote resource);

    List<Vote> findVotesReview(Long reviewId);
    // void deleteById(Long id, long parseLong);

}
