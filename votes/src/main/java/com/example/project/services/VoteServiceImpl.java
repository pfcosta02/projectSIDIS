package com.example.project.services;

import com.example.project.model.Review;
import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;
import com.example.project.rabbitmq.Sender;
import com.example.project.repositories.ReviewRepository;
import com.example.project.repositories.VoteRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository repository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private Sender sender;

    public String exchange = "vote_fanout";

    @Override
    public Vote create(final Vote resource) {

        final var optionalReview = reviewRepository.findByUUID(resource.getUuid());

        if(optionalReview.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review dont exists");
        }

        final Vote obj = Vote.newFrom(resource);
        sender.send(exchange, obj);

        return repository.save(obj);
    }

    @Override
    public Vote create(final Long id, final Vote resource) {
        throw new UnsupportedOperationException();
    }

}
