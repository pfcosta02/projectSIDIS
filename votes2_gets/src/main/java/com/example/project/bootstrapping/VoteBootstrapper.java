package com.example.project.bootstrapping;

import com.example.project.model.Vote;
import com.example.project.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Spring will load and execute all components that implement the interface
 * CommandLinerunner on startup, so we will use that as a way to bootstrap some
 * data for testing purposes.
 * <p>
 * In order to enable this bootstraping make sure you activate the spring
 * profile "bootstrap" in application.properties
 *
 * @author pgsou
 *
 */
@Component
@Profile("bootstrap")
public class VoteBootstrapper implements CommandLineRunner {
    // setText setRating setVote setDataTime setStatus
    @Autowired
    private VoteRepository voteRepo;

    @Override
    public void run(String... args) throws Exception {
        if (voteRepo.findById(1L).isEmpty()) {
            Vote f1 = new Vote();
            f1.setCustomerId(3L);
            f1.setVote("UpVote");
            f1.setReviewId(1L);
            voteRepo.save(f1);
        }
        if (voteRepo.findById(2L).isEmpty()) {
            Vote f1 = new Vote();
            f1.setCustomerId(3L);
            f1.setVote("UpVote");
            f1.setReviewId(1L);
            voteRepo.save(f1);
        }

    }

}
