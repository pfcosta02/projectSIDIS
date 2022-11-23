package com.example.project.services;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            String url = "http://localhost:8086/api/reviews/product/"+ productSku + "/date/votes/anotherApp";

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
            String url = "http://localhost:8086/api/reviews/product/"+ productSku + "/date/anotherApp";

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
            String url = "http://localhost:8086/api/reviews/pending/anotherApp";

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
            String url = "http://localhost:8086/api/reviews/customer/"+ customerId + "/anotherApp";

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
    public Review create(final Review resource) {
        // construct a new object based on data received by the service to ensure domain
        // invariants are met
        try {
            String url = "http://localhost:8090/api/products/sku/" + resource.getProductSku();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());


            if (response.body().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        final Review obj = Review.newFrom(resource);

        return repository.save(obj);
    }

    @Override
    public Review create(final Long id, final Review resource) {
        // Foo's id is automatically assigned by the service, so we do not support
        // creation of objects where the client sets the id
        throw new UnsupportedOperationException();
    }

    @Override
    public Review partialUpdate(final Long id, final Review resource, final long desiredVersion) {
        // first let's check if the object exists so we don't create a new object with
        // save
        final var review = repository.findById(id)
                .orElseThrow(() -> new MyResourceNotFoundException("Cannot update an object that does not yet exist"));

        // since we got the object from the database we can check the version in memory
        // and apply the patch
        review.applyPatch(resource, desiredVersion);

        // in the meantime some other user might have changed this object on the
        // database, so concurrency control will still be applied when we try to save
        // this updated object
        return repository.save(review);
    }



    @Override
    public void deleteById(final Long id, final long desiredVersion) {
        repository.deleteByIdIfMatch(id, desiredVersion);
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
