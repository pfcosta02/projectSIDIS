package com.example.project.controllers;

import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;
import com.example.project.services.VoteService;
import com.example.project.usermanagement.model.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
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

    @Autowired
    private AmqpTemplate amqpTemplate;

    public String exchange = "vote_fanout";

    @Operation(summary = "Make a vote in a review")
    @RolesAllowed(Role.CUSTOMER)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> create(@Valid  @RequestBody final Vote resource,final WebRequest request2) {

        final String auth = request2.getHeader("Authorization");

        String newToken = auth.replace("Bearer ", "");
        Jwt decodedToken = this.jwtDecoder.decode(newToken);
        String subject = (String) decodedToken.getClaims().get("sub");
        Long userId = Long.valueOf(subject.split(",")[0]);

        resource.setCustomerId(userId);

        final var vote = service.create(resource);
        amqpTemplate.convertAndSend(exchange, "", vote);
        return ResponseEntity.status(HttpStatus.CREATED).eTag(Long.toString(vote.getVersion())).body(vote);
    }

}
