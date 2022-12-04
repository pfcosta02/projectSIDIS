package com.example.project.services;

import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;

import java.util.List;
import java.util.UUID;

public interface VoteService {

    List<VoteDTO> findVotesReview(UUID reviewId);

}
