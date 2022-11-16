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
    public Integer rating;
    public Integer upVote;
    public Integer downVote;
    public String dataTime;
    public String status;
    public String productSku;
    public Long customerId;
    public String funnyFact;

    public ReviewDTO(Long reviewId, UUID uuid, Integer rating, Integer upVote, Integer downVote, String dataTime, String status, String productSku, Long customerId, String funnyFact) {
        this.reviewId = reviewId;
        this.uuid = uuid;
        this.rating = rating;
        this.upVote = upVote;
        this.downVote = downVote;
        this.dataTime = dataTime;
        this.status = status;
        this.productSku = productSku;
        this.customerId = customerId;
        this.funnyFact = funnyFact;
    }
}
