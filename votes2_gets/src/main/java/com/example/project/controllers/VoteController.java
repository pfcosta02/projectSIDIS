package com.example.project.controllers;

import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;
import com.example.project.services.VoteService;
import com.example.project.usermanagement.model.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Tag(name = "Votes", description = "Endpoints for managing votes")
@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private static final Logger logger = LoggerFactory.getLogger(VoteController.class);

    @Autowired
    private VoteService service;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Operation(summary = "Shows catalog of reviews")
    @GetMapping(value = "/{reviewId}")
    public List<VoteDTO> findVotesReview(@PathVariable("reviewId") final Long reviewId) throws IOException, InterruptedException {
        String url = "http://localhost:8093/api/reviews/" + reviewId;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found");
        }

        return service.findVotesReview(reviewId);
    }

    @Operation(summary = "Shows catalog of reviews")
    @GetMapping(value = "/{reviewId}/anotherapp")
    public List<VoteDTO> findVotesReviewAnotherApp(@PathVariable("reviewId") final Long reviewId) {
        return service.findVotesReviewAnotherApp(reviewId);
    }

}
