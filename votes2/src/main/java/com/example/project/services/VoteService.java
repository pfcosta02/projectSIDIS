package com.example.project.services;

import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;

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

    // void deleteById(Long id, long parseLong);

}
