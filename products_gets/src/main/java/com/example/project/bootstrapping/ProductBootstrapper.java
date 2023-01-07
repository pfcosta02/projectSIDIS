package com.example.project.bootstrapping;


import com.example.project.rabbitmq.Rpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.project.model.Product;
import com.example.project.repositories.ProductRepository;

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
public class ProductBootstrapper implements CommandLineRunner {
    // setName setDescription setSku
    @Autowired
    private Rpc rpc;

    @Override
    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void run(String... args) {
        rpc.helper();
        rpc.helper2();
    }

}
