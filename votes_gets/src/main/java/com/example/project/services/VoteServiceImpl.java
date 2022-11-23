package com.example.project.services;

import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;
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

    @Override
    public List<VoteDTO> findVotesReview(Long reviewId) {
        List<Vote> allVotes = repository.findVotesReview(reviewId);
        List<VoteDTO> allVotesDto = new ArrayList<>();

        for(int i=0; i < allVotes.size(); i++) {
            VoteDTO vote = new VoteDTO(allVotes.get(i).getVote(), allVotes.get(i).getReviewId(), allVotes.get(i).getCustomerId());
            allVotesDto.add(vote);
        }

        try {
            String url = "http://localhost:8096/api/votes/" + reviewId + "/anotherapp";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
            }

            ObjectMapper mapper = new ObjectMapper();
            List<VoteDTO> votes = mapper.readValue(response.body(), new TypeReference<List<VoteDTO>>() {});

            for(int i=0; i < votes.size(); i++) {
                allVotesDto.add(votes.get(i));
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return allVotesDto;
    }

    public List<VoteDTO> findVotesReviewAnotherApp(Long reviewId) {
        List<Vote> allVotes = repository.findVotesReview(reviewId);
        List<VoteDTO> allVotesDto = new ArrayList<>();

        for(int i=0; i < allVotes.size(); i++) {
            VoteDTO vote = new VoteDTO(allVotes.get(i).getVote(), allVotes.get(i).getReviewId(), allVotes.get(i).getCustomerId());
            allVotesDto.add(vote);
        }

        return allVotesDto;
    }
}
