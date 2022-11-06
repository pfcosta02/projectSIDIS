package com.example.project.services;

import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;

import java.io.IOException;
import java.util.List;

public interface VoteService {
    /**
     * Create a new Vote and assign its id.
     *
     * @param resource
     * @return
     */
    Vote create(Vote resource);
    Vote create(Long id, Vote resource);

    List<VoteDTO> findVotesReview(Long reviewId) throws IOException, InterruptedException;
    // void deleteById(Long id, long parseLong);

}
