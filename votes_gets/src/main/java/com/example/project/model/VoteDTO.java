package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class VoteDTO {

    public final String vote;
    public UUID reviewUuid;
    public Long customerId;

    public VoteDTO(@JsonProperty("vote") String vote, @JsonProperty("reviewId") UUID reviewUuid, @JsonProperty("customerId") Long customerId) {
        this.vote = vote;
        this.reviewUuid = reviewUuid;
        this.customerId = customerId;
    }
}
