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

    @Column (name = "status")
    private String status;

    @Column (name = "text")
    private String text;

    @Column (name = "rating")
    private Integer rating;

    @Column (name = "productSku")
    private String productSku;

    @Version
    private Long version;

    public Vote() {
    }

    public Vote(String vote, UUID uuid, Long customerId) {
        this.vote = vote;
        this.uuid = uuid;
        this.customerId = customerId;
        this.status="approved";
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

        if (resource.getStatus() != null) {
            obj.setStatus(resource.status);
        } else {
            obj.setStatus("approved");
        }

        return obj;
    }

    public void applyPatch(final long desiredVersion) {
        // check current version
        if (this.version != desiredVersion) {
            throw new StaleObjectStateException("Object was already modified by another user", this.vote);
        }
        // apply patch only if the field was sent in the request. we do not allow to
        // change the name attribute so we simply ignore it

        this.setStatus("approved");
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

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }
}
