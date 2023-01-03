package com.example.project.bootstrapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.project.model.Review;
import com.example.project.repositories.ReviewRepository;

import java.util.UUID;

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
public class ReviewBootstrapper implements CommandLineRunner {
    // setText setRating setVote setDataTime setStatus
    @Autowired
    private ReviewRepository reviewRepo;

    @Override
    public void run(String... args) throws Exception {
        if (reviewRepo.findById(1L).isEmpty()) {
            Review f1 = new Review(1L);
            f1.setText("Bom");
            f1.setRating(4);
            f1.setUpVote(0);
            f1.setDownVote(0);
            UUID uuid=UUID.fromString("fd3b2b1f-e246-46d0-8b0f-c10ae397c8fe");
            f1.setUuid(uuid);
            f1.setDataTime("06-11-2022 23:30:00");
            f1.setFunnyFact();
            f1.setStatus("Pending");
            f1.setProductSku("123456789A");
            f1.setCustomerId(3L);
            reviewRepo.save(f1);
        }

    }

}
