package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.io.IOException;
import java.util.UUID;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (name = "vote")
    private String vote;

    @Column (name = "GUID")
    private UUID uuid;

    @Column (name = "customerId")
    private Long customerId;

    @Version
    private Long version;

    public Vote() {
    }

    public Vote(String vote, UUID uuid, Long customerId) {
        this.vote = vote;
        this.uuid = uuid;
        this.customerId = customerId;
    }

    public static Vote newFrom(final Vote resource) {
        final Vote obj = new Vote();

        if (resource.getVote() != null) {
            if (resource.getVote().equals("UpVote") || resource.getVote().equals("DownVote")) {
                obj.setVote(resource.vote);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Vote");
            }
        }
        if (resource.getUuid() != null) {
            obj.setReviewId(resource.uuid);
        }
        if (resource.getCustomerId() != null) {
            obj.setCustomerId(resource.customerId);
        }

        return obj;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setReviewId(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getVersion() {
        return version;
    }
}
