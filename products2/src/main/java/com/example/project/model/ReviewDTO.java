package com.example.project.model;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.*;

import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.StaleObjectStateException;

public class ReviewDTO {

    public Long reviewId;

    public String text;

    public Integer rating;

    public Integer upVote;

    public Integer downVote;

    public String dataTime;

    public String status;

    public Long productId;

    public Long customerId;

    public String funnyFact;

    public Long version;

    public ReviewDTO(){}

    public ReviewDTO(Long reviewId, String text, Integer rating,Integer upVote, Integer downVote, String dataTime, String status, Long prod, Long cust, String funnyFact, Long version) {
        this.reviewId = reviewId;
        this.text = text;
        this.rating = rating;
        this.upVote = upVote;
        this.downVote = downVote;
        this.dataTime = dataTime;
        this.productId = prod;
        this.customerId = cust;
        this.status = status;
        this.funnyFact = funnyFact;
        this.version = version;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getText() {
        return text;
    }

    public Integer getRating() {
        return rating;
    }

    public Integer getUpVote() {
        return upVote;
    }

    public Integer getDownVote() {
        return downVote;
    }

    public String getDataTime() {
        return dataTime;
    }

    public String getStatus() {
        return status;
    }

    public Long getVersion() {
        return version;
    }

    public String getFunnyFact() {
        return funnyFact;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFunnyFact(String funnyFact){
        this.funnyFact = funnyFact;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setUpVote(Integer upVote) {
        this.upVote = upVote;
    }

    public void setDownVote(Integer downVote) {
        this.downVote = downVote;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProductId(Long id) {
        this.productId = id;
    }

    public void setCustomerId(Long id) {
        this.customerId = id;
    }

}
