package com.example.project.services;

import com.example.project.model.Review;
import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface VoteService {
    /**
     * Create a new Vote and assign its id.
     *
     * @param resource
     * @return
     */
    Vote create(Vote resource);
    Vote create(Long id, Vote resource);

    Vote partialUpdate(UUID uuid, Vote resource, long parseLong);

    // void deleteById(Long id, long parseLong);

}
