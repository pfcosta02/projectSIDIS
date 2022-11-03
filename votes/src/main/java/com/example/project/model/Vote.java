package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.StaleObjectStateException;

import javax.persistence.*;
import java.io.IOException;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (name = "vote")
    private String vote;

    @Column (name = "reviewId")
    private Long reviewId;

    @Column (name = "customerId")
    private Long customerId;

    @Version
    private Long version;

    public Vote() {
    }

    public Vote(String vote, Long reviewId, Long customerId) {
        this.vote = vote;
        this.reviewId = reviewId;
        this.customerId = customerId;
    }

    public static Vote newFrom(final Vote resource) {
        final Vote obj = new Vote();

        if (resource.getVote() != null) {
            if (resource.getVote().equals("UpVote") || resource.getVote().equals("DownVote")) {
                obj.setVote(resource.vote);
            }
        }
        if (resource.getReviewId() != null) {
            obj.setReviewId(resource.reviewId);
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

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
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
