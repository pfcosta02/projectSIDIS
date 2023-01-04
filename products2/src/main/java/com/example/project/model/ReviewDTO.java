package com.example.project.model;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.*;

import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.util.UUID;
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

    public ReviewDTO(Long reviewId, UUID uuid, String text, Integer rating,Integer upVote, Integer downVote, String dataTime, String status, String prod, Long cust, String funnyFact, Long version) {
        this.reviewId = reviewId;
        this.uuid = uuid;
        this.text = text;
        this.rating = rating;
        this.upVote = upVote;
        this.downVote = downVote;
        this.dataTime = dataTime;
        this.productSku = prod;
        this.customerId = cust;
        this.status = status;
        this.funnyFact = funnyFact;
        this.version = version;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public String getProductSku() {
        return productSku;
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

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public void setCustomerId(Long id) {
        this.customerId = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
