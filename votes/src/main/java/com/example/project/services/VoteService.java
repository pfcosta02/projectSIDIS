package com.example.project.services;

import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;

public interface VoteService {

    Vote create(final VoteDTO resource, int idReview);
}
