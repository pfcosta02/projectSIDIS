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

import java.util.List;
@Service
public class VoteServiceImpl implements VoteService{

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Vote create(VoteDTO resource, int idReview) {
        return null;
    }

        /*public Vote create(final VoteDTO resource, int idReview){
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            Review review = repository.findByIdReview(idReview);
            if (review == null){
                throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found");
            }
            if(review.getStatus() == "PENDING" || review.getStatus() == "REJECTED"){
                throw  new ResponseStatusException(HttpStatus.CONFLICT, "Review pending or rejected");
            }
            User user = userRepository.findUserByUsername(username);
            if (user == null){
                throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
            }
            int aux = 0;
            for (int i = 0 ; i < review.getVotes().size(); i++){
                if (review.getVotes().get(i).getUser().getId() == user.getId()){
                    aux = 1;
                }
                else {
                    aux = 2;
                }
            }
            if (aux == 1){
                throw  new ResponseStatusException(HttpStatus.FORBIDDEN, "Duplicated Vote");
            }
            else {
                review.setTotalVotes(review.getTotalVotes()+1);
                if (resource.getVote() == true){
                    review.setUpVotes(review.getUpVotes()+1);
                }
                else {
                    review.setDownVotes(review.getDownVotes()+1);
                }
                Vote vote = new Vote();
                vote.setVote(resource.getVote());
                vote.setUser(user);
                vote.setReview(review);
                return voteRepository.save(vote);
            }
        }*/


        /*public Product findAllReviewsWithVotes(String sku,Integer pageNo,Integer pageSize) {
            Product product = productRepository.findBySkuProduct(sku);
            Pageable paging = PageRequest.of(pageNo, pageSize);
            if (product == null){
                throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found ");
            }
            Page<Review> review =  repository.findAllWithVotes(sku,paging);
            List<Review> reviews = review.getContent();
            product.setReviews(reviews);
            return product;
        }*/
}
