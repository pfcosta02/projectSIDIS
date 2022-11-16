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



@Entity
@Table(name = "reviews")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Review implements Serializable {
    // database primary key
    // since this field is autogenerated by the database there is not setId()
    // method, only a getId() method
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewId;

    @Column (name = "GUID")
    private UUID uuid;

    @Column (name = "text")
    @Size(min = 1, max = 100)
    private String text;

    @Column (name = "rating")
    private Integer rating;

    @Column (name = "upVote")
    private Integer upVote;

    @Column (name = "downVote")
    private Integer downVote;

    @Column (name = "dataTime")
    private String dataTime;

    @Column (name = "status")
    private String status;

    @Column (name = "productSku")
    private String productSku;

    @Column (name = "customerId")
    private Long customerId;

    @Column (name = "funnyFact")
    private String funnyFact;

    @Version
    private Long version;

    protected Review() {}

    public Review(Long reviewId, String text, Integer rating, String dataTime) throws IOException {
        this.reviewId = reviewId;
        this.text = text;
        this.rating = rating;
        this.upVote = 0;
        this.downVote = 0;
        this.dataTime = dataTime;
        this.status = "Pending";
        this.funnyFact = retrieveDataFromApi(dataTime);
    }

    // Constructor for tests
    public Review(String text) {
        this.text = text;
    }
    public Review(long reviewId) {
        this.reviewId = reviewId;
        this.uuid = UUID.randomUUID();
    }

    /**
     * Factory method to create a new object based on the data of another object.
     * Does not copy the id of the original object.
     *
     * @param resource
     * @return
     */
    public static Review newFrom(final Review resource) {
        final Review obj = new Review();
        if (resource.getText() != null) {
            obj.setText(resource.text);
        }
        if (resource.getRating() != null) {
            obj.setRating(resource.rating);
        }
        if (resource.getDataTime() != null) {
            obj.setDataTime(resource.dataTime);
        } else {
            obj.addDataTime();
        }
        if (resource.getProductSku() != null) {
            obj.setProductSku(resource.productSku);
        }
        if (resource.getCustomerId() != null) {
            obj.setCustomerId(resource.customerId);
        }
        if (resource.getUuid() == null) {
            obj.setUuid(UUID.randomUUID());
        }
        obj.setStatus("Pending");
        obj.setUpVote(0);
        obj.setDownVote(0);
        try {
            obj.setFunnyFact();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public void applyPatch(final Review resource, final long desiredVersion) {
        // check current version
        if (this.version != desiredVersion) {
            throw new StaleObjectStateException("Object was already modified by another user", this.reviewId);
        }
        // apply patch only if the field was sent in the request. we do not allow to
        // change the name attribute so we simply ignore it

        this.setStatus(resource.status);
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

    public void setFunnyFact() throws IOException {
        this.funnyFact = retrieveDataFromApi(this.dataTime);;
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

    public void setProductSku(String sku) {
        this.productSku = sku;
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

    public void addDataTime() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        this.dataTime = formattedDate;
    }


    public String retrieveDataFromApi(String dataTime) throws IOException {
        String baseUrl = "http://www.numbersapi.com/";
        String url = baseUrl + dataTime.substring(3,5) + "/" + dataTime.substring(0,2) + "/date";

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(url);

            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            return result;
        }

    }
}
