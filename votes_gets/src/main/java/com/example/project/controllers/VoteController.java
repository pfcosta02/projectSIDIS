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
import java.util.UUID;

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
    @GetMapping(value = "/{uuid}")
    public List<VoteDTO> findVotesReview(@PathVariable("uuid") final UUID uuid){
        return service.findVotesReview(uuid);
    }

}
