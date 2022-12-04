package com.example.project.services;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.project.exceptions.MyResourceNotFoundException;
import com.example.project.model.ReviewDTO;
import com.example.project.model.VoteDTO;
import com.example.project.views.ReviewView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.project.model.Review;
import com.example.project.repositories.ReviewRepository;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Override
    public List<ReviewDTO> findApprovedReviews(final String productSku) {

        try {
            String url = "http://localhost:8090/api/products/sku/" + productSku;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());


            if(response.statusCode() != 200) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        List<Review> allReviews = repository.findApprovedReviews(productSku);
        List<ReviewDTO> allReviewsDto = new ArrayList<>();

        for(int i=0; i < allReviews.size(); i++) {
            ReviewDTO product = new ReviewDTO(allReviews.get(i).getReviewId(),allReviews.get(i).getUuid(), allReviews.get(i).getRating(),allReviews.get(i).getUpVote(),allReviews.get(i).getDownVote(),allReviews.get(i).getDataTime(),allReviews.get(i).getStatus(),allReviews.get(i).getProductSku(),allReviews.get(i).getCustomerId(),allReviews.get(i).getFunnyFact());
            allReviewsDto.add(product);
        }

        return allReviewsDto;
    }

    @Override
    public List<ReviewDTO> findApprovedReviewsByDate(final String productSku) {
        try {
            String url = "http://localhost:8090/api/products/sku/" + productSku;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());


            if(response.statusCode() != 200) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        List<Review> allReviews = repository.findApprovedReviewsByDate(productSku);
        List<ReviewDTO> allReviewsDto = new ArrayList<>();

        for(int i=0; i < allReviews.size(); i++) {
            ReviewDTO product = new ReviewDTO(allReviews.get(i).getReviewId(), allReviews.get(i).getUuid(), allReviews.get(i).getRating(),allReviews.get(i).getUpVote(),allReviews.get(i).getDownVote(),allReviews.get(i).getDataTime(),allReviews.get(i).getStatus(),allReviews.get(i).getProductSku(),allReviews.get(i).getCustomerId(),allReviews.get(i).getFunnyFact());
            allReviewsDto.add(product);
        }

        return allReviewsDto;
    }

    @Override
    public List<ReviewDTO> findAllPending(WebRequest request2) {

        List<Review> allReviews = repository.findAllPending();
        List<ReviewDTO> allReviewsDto = new ArrayList<>();

        for(int i=0; i < allReviews.size(); i++) {
            ReviewDTO product = new ReviewDTO(allReviews.get(i).getReviewId(), allReviews.get(i).getUuid(), allReviews.get(i).getRating(),allReviews.get(i).getUpVote(),allReviews.get(i).getDownVote(),allReviews.get(i).getDataTime(),allReviews.get(i).getStatus(),allReviews.get(i).getProductSku(),allReviews.get(i).getCustomerId(),allReviews.get(i).getFunnyFact());
            allReviewsDto.add(product);
        }

        return allReviewsDto;
    }

    @Override
    public Optional<Review> findOne(final Long reviewId) {
        return repository.findById(reviewId);
    }


    @Override
    public Optional<Review> findByUUID(final UUID uuid) {
        final var optionalReview = repository.findByUUID(uuid);

        if (optionalReview.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found");
        }

        Review p = optionalReview.get();

        return optionalReview;
    }

    @Override
    public List<ReviewDTO> findMyReviews(final Long customerId, WebRequest request2) {
        List<Review> allReviews = repository.findMyReviews(customerId);
        List<ReviewDTO> allReviewsDto = new ArrayList<>();

        for(int i=0; i < allReviews.size(); i++) {
            ReviewDTO product = new ReviewDTO(allReviews.get(i).getReviewId(), allReviews.get(i).getUuid(), allReviews.get(i).getRating(),allReviews.get(i).getUpVote(),allReviews.get(i).getDownVote(),allReviews.get(i).getDataTime(),allReviews.get(i).getStatus(),allReviews.get(i).getProductSku(),allReviews.get(i).getCustomerId(),allReviews.get(i).getFunnyFact());
            allReviewsDto.add(product);
        }

        try {
            String url = "http://localhost:8080/api/customer/user/" + customerId;

            final String auth = request2.getHeader("Authorization");

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", auth)
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return allReviewsDto;
    }

    @Override
    public void getVotes(Review review, List<VoteDTO> votes) {

        for (int i = 0; i < votes.size(); i++) {
            if(votes.size() != review.getUpVote() + review.getDownVote()) {
                if(votes.get(i).vote.equals("UpVote")){
                    review.setUpVote(review.getUpVote()+1);
                } else if (votes.get(i).vote.equals("DownVote")) {
                    review.setDownVote(review.getDownVote()+1);
                }
            }

        }

        repository.save(review);
    }
}
