package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class VoteDTO {

    public final String vote;
    public UUID uuid;
    public Long reviewId;
    public Long customerId;

    public VoteDTO(@JsonProperty("vote") String vote, @JsonProperty("UUID") UUID uuid,@JsonProperty("reviewId") Long reviewId, @JsonProperty("customerId") Long customerId) {
        this.vote = vote;
        this.uuid = uuid;
        this.reviewId = reviewId;
        this.customerId = customerId;
    }
}
