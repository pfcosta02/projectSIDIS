package com.example.project.services;

import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;
import com.example.project.repositories.VoteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.UUID;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository repository;

    @Override
    public List<VoteDTO> findVotesReview(UUID uuid) {
        List<Vote> allVotes = repository.findVotesReview(uuid);
        List<VoteDTO> allVotesDto = new ArrayList<>();

        for(int i=0; i < allVotes.size(); i++) {
            VoteDTO vote = new VoteDTO(allVotes.get(i).getVote(), allVotes.get(i).getUuid(), allVotes.get(i).getCustomerId());
            allVotesDto.add(vote);
        }

        return allVotesDto;
    }
}
