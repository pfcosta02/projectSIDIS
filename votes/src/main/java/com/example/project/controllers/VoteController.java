package com.example.project.controllers;

import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;
import com.example.project.services.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "Votes", description = "Endpoints for managing votes")
@RestController
@RequestMapping("/votes")
public class VoteController {

    private static final Logger logger = LoggerFactory.getLogger(VoteController.class);

    @Autowired
    private VoteService service;

    @Operation(summary = "Make a vote in a review")
    @PostMapping(value = "/{idReview}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> create(@RequestBody final VoteDTO resource, @PathVariable("idReview") final int idReview) throws IOException {
        final var vote = service.create(resource,idReview);
        return ResponseEntity.ok().body(vote);
    }

}