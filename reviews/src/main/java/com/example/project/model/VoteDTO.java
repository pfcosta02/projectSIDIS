package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Version;

public class VoteDTO {

    public int id;

    public String vote;

    public int reviewId;

    public int customerId;

    public int version;

    public VoteDTO(){}

    public VoteDTO(int id, String vote, int reviewId, int customerId, int version) {
        this.vote = vote;
        this.reviewId = reviewId;
        this.customerId = customerId;
        this.id = id;
        this.version = version;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
