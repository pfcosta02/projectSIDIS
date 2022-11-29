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

        try {
            String url = "http://localhost:8094/api/reviews/product/"+ productSku + "/date/votes/anotherApp";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found");
            }

            ObjectMapper mapper = new ObjectMapper();
            List<ReviewDTO> products = mapper.readValue(response.body(), new TypeReference<List<ReviewDTO>>() {});

            for(int i=0; i < products.size(); i++) {
                allReviewsDto.add(products.get(i));
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return allReviewsDto;
    }

    @Override
    public List<ReviewDTO> findApprovedReviewsAll(final String productSku) {
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
            ReviewDTO product = new ReviewDTO(allReviews.get(i).getReviewId(),allReviews.get(i).getUuid(),allReviews.get(i).getRating(),allReviews.get(i).getUpVote(),allReviews.get(i).getDownVote(),allReviews.get(i).getDataTime(),allReviews.get(i).getStatus(),allReviews.get(i).getProductSku(),allReviews.get(i).getCustomerId(),allReviews.get(i).getFunnyFact());
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

        try {
            String url = "http://localhost:8094/api/reviews/product/"+ productSku + "/date/anotherApp";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found");
            }

            ObjectMapper mapper = new ObjectMapper();
            List<ReviewDTO> products = mapper.readValue(response.body(), new TypeReference<List<ReviewDTO>>() {});

            for(int i=0; i < products.size(); i++) {
                allReviewsDto.add(products.get(i));
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return allReviewsDto;
    }

    public List<ReviewDTO> findApprovedReviewsByDateAll(final String productSku) {
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
            ReviewDTO product = new ReviewDTO(allReviews.get(i).getReviewId(),allReviews.get(i).getUuid(),allReviews.get(i).getRating(),allReviews.get(i).getUpVote(),allReviews.get(i).getDownVote(),allReviews.get(i).getDataTime(),allReviews.get(i).getStatus(),allReviews.get(i).getProductSku(),allReviews.get(i).getCustomerId(),allReviews.get(i).getFunnyFact());
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

        try {
            String url = "http://localhost:8094/api/reviews/pending/anotherApp";

            final String auth = request2.getHeader("Authorization");

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", auth)
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reviews Not Found");
            }

            ObjectMapper mapper = new ObjectMapper();
            List<ReviewDTO> products = mapper.readValue(response.body(), new TypeReference<List<ReviewDTO>>() {});

            for(int i=0; i < products.size(); i++) {
                allReviewsDto.add(products.get(i));
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return allReviewsDto;
    }

    @Override
    public List<ReviewDTO> findAllPendingAll() {

        List<Review> allReviews = repository.findAllPending();
        List<ReviewDTO> allReviewsDto = new ArrayList<>();

        for(int i=0; i < allReviews.size(); i++) {
            ReviewDTO product = new ReviewDTO(allReviews.get(i).getReviewId(),allReviews.get(i).getUuid(),allReviews.get(i).getRating(),allReviews.get(i).getUpVote(),allReviews.get(i).getDownVote(),allReviews.get(i).getDataTime(),allReviews.get(i).getStatus(),allReviews.get(i).getProductSku(),allReviews.get(i).getCustomerId(),allReviews.get(i).getFunnyFact());
            allReviewsDto.add(product);
        }

        return allReviewsDto;
    }

    @Override
    public Optional<Review> findOne(final Long reviewId) {
        return repository.findById(reviewId);
    }

    @Override
    public Optional<ReviewDTO> findByUUID(final UUID uuid) {
        final var optionalReview = repository.findByUUID(uuid);

        if (optionalReview.isEmpty()) {
            try {
                String url = "http://localhost:8094/api/reviews/" + uuid + "/anotherApp";

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();

                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());

                if(response.statusCode() != 200) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found");
                }

                ObjectMapper mapper = new ObjectMapper();

                ReviewDTO dto = mapper.readValue(response.body(), ReviewDTO.class);

                return Optional.of(dto);

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

        Review p = optionalReview.get();

        ReviewDTO dto = new ReviewDTO(p.getReviewId(), p.getUuid(), p.getRating(), p.getUpVote(), p.getDownVote(), p.getDataTime(), p.getStatus(), p.getProductSku(), p.getCustomerId(), p.getFunnyFact());

        return Optional.of(dto);
    }


    @Override
    public Optional<ReviewDTO> findByUUIDAll(final UUID uuid) {
        final var optionalReview = repository.findByUUID(uuid);

        if (optionalReview.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found");
        }

        Review p = optionalReview.get();

        ReviewDTO dto = new ReviewDTO(p.getReviewId(), p.getUuid(), p.getRating(), p.getUpVote(), p.getDownVote(), p.getDataTime(), p.getStatus(), p.getProductSku(), p.getCustomerId(), p.getFunnyFact());

        return Optional.of(dto);
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
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            String url = "http://localhost:8094/api/reviews/customer/"+ customerId + "/anotherApp";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found");
            }

            ObjectMapper mapper = new ObjectMapper();
            List<ReviewDTO> products = mapper.readValue(response.body(), new TypeReference<List<ReviewDTO>>() {});

            for(int i=0; i < products.size(); i++) {
                allReviewsDto.add(products.get(i));
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return allReviewsDto;
    }

    @Override
    public List<ReviewDTO> findMyReviewsAll(final Long customerId, WebRequest request2) {
        List<Review> allReviews = repository.findMyReviews(customerId);
        List<ReviewDTO> allReviewsDto = new ArrayList<>();

        for(int i=0; i < allReviews.size(); i++) {
            ReviewDTO product = new ReviewDTO(allReviews.get(i).getReviewId(),allReviews.get(i).getUuid(),allReviews.get(i).getRating(),allReviews.get(i).getUpVote(),allReviews.get(i).getDownVote(),allReviews.get(i).getDataTime(),allReviews.get(i).getStatus(),allReviews.get(i).getProductSku(),allReviews.get(i).getCustomerId(),allReviews.get(i).getFunnyFact());
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
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
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
