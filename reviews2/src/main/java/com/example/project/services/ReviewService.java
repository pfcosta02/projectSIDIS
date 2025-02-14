package com.example.project.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.project.model.Review;
import com.example.project.model.ReviewDTO;
import com.example.project.model.VoteDTO;
import com.example.project.views.ReviewView;
import org.springframework.web.context.request.WebRequest;

public interface ReviewService {

    /**
     * Create a new Review and assign its id.
     *
     * @param resource
     * @return
     */
    Review create(Review resource);

    /**
     * Creates a new Review with the specified id.
     *
     * @param id
     * @param resource
     * @return
     */
    Review create(Long id, Review resource);
    void deleteById(UUID uuid, long parseLong);

    /**
     * Partial updates an existing Foo.
     *
     * @param uuid
     * @param resource  "patch document"
     * @param parseLong
     * @return
     */
    Review partialUpdate(UUID uuid, Review resource, long parseLong);
}

