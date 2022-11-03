package com.example.project.services;
import java.util.List;
import java.util.Optional;

import com.example.project.exceptions.MyResourceNotFoundException;
import com.example.project.model.VoteDTO;
import com.example.project.views.ReviewView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.project.model.Review;
import com.example.project.repositories.ReviewRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Override
    public Iterable<Review> findAll() {
        return repository.findAll();
    }

    @Override
    public Iterable<Review> findApprovedReviews(final Long productId) {
        return repository.findApprovedReviews(productId);
    }

    @Override
    public Iterable<Review> findApprovedReviewsByDate(final Long productId) {
        return repository.findApprovedReviewsByDate(productId);
    }

    @Override
    public Iterable<Review> findAllPending() {
        return repository.findAllPending();
    }

    @Override
    public Optional<Review> findOne(final Long reviewId) {
        return repository.findById(reviewId);
    }

    @Override
    public Iterable<ReviewView> findMyReviews(final Long customerId) {
        return repository.findMyReviews(customerId);
    }

    @Override
    public Review create(final Review resource) {
        // construct a new object based on data received by the service to ensure domain
        // invariants are met
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
            if(votes.get(i).vote.equals("UpVote")){
                review.setUpVote(review.getUpVote()+1);
            } else if (votes.get(i).vote.equals("DownVote")) {
                review.setDownVote(review.getDownVote()+1);
            }
        }
    }
}
