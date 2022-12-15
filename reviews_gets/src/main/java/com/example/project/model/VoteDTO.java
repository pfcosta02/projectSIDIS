package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Version;
import java.util.UUID;

public class VoteDTO {

    public Long id;

    public String vote;

    public UUID uuid;

    public Long customerId;

    public Long version;

    public VoteDTO(){}

    public VoteDTO(Long id, String vote, UUID uuid, Long customerId, Long version) {
        this.vote = vote;
        this.uuid = uuid;
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
