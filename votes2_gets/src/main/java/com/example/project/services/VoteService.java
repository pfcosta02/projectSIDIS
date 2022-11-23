package com.example.project.services;

import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;

import java.util.List;

public interface VoteService {

    List<VoteDTO> findVotesReview(Long reviewId);
    List<VoteDTO> findVotesReviewAnotherApp(Long reviewId);

}
