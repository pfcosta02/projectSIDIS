package com.example.project.services;

import com.example.project.model.Vote;
import com.example.project.model.VoteDTO;
import com.example.project.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository repository;

    @Override
    public Vote create(final Vote resource) {
        final Vote obj = Vote.newFrom(resource);
        return repository.save(obj);
    }

    @Override
    public Vote create(final Long id, final Vote resource) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<VoteDTO> findVotesReview(Long reviewId) {
        List<Vote> allVotes = repository.findVotesReview(reviewId);
        List<VoteDTO> allVotesDto = new ArrayList<>();

        for(int i=0; i < allVotes.size(); i++) {
            VoteDTO vote = new VoteDTO(allVotes.get(i).getVote(), allVotes.get(i).getReviewId(), allVotes.get(i).getCustomerId());
            allVotesDto.add(vote);
        }

        return allVotesDto;
    }
}
