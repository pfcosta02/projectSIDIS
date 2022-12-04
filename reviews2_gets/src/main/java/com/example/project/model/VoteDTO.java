package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Version;
import java.util.UUID;

public class VoteDTO {

    public int id;

    public String vote;

    public UUID uuid;

    public int customerId;

    public int version;

    public VoteDTO(){}

    public VoteDTO(int id, String vote, UUID uuid, int customerId, int version) {
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
