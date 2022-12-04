package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class VoteDTO {

    public final String vote;
    public UUID uuid;
    public Long customerId;

    public VoteDTO(@JsonProperty("vote") String vote, @JsonProperty("uuid") UUID uuid, @JsonProperty("customerId") Long customerId) {
        this.vote = vote;
        this.uuid = uuid;
        this.customerId = customerId;
    }
}
