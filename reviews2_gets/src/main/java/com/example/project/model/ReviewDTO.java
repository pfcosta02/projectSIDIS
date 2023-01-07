package com.example.project.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.UUID;

public class ReviewDTO {


    public Long reviewId;
    public UUID uuid;
    public String text;
    public Integer rating;
    public Integer upVote;
    public Integer downVote;
    public String dataTime;
    public String status;
    public String productSku;
    public Long customerId;
    public String funnyFact;
    public Long version;

    public ReviewDTO(){}
    public ReviewDTO(Long reviewId, UUID uuid, String text, Integer rating, Integer upVote, Integer downVote, String dataTime, String status, String productSku, Long customerId, String funnyFact, Long version) {
        this.reviewId = reviewId;
        this.uuid = uuid;
        this.text = text;
        this.rating = rating;
        this.upVote = upVote;
        this.downVote = downVote;
        this.dataTime = dataTime;
        this.status = status;
        this.productSku = productSku;
        this.customerId = customerId;
        this.funnyFact = funnyFact;
        this.version = version;
    }
}
