package com.example.project.services;

import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;

public interface VoteService {

    Vote create(Vote resource);
    Vote create(Long id, Vote resource);

    Iterable<Vote> findByReview(Long reviewId);
    // void deleteById(Long id, long parseLong);

}
