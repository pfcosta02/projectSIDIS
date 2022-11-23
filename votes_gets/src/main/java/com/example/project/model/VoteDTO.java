package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VoteDTO {

    public final String vote;
    public Long reviewId;
    public Long customerId;

    public VoteDTO(@JsonProperty("vote") String vote, @JsonProperty("reviewId") Long reviewId, @JsonProperty("customerId") Long customerId) {
        this.vote = vote;
        this.reviewId = reviewId;
        this.customerId = customerId;
    }
}
