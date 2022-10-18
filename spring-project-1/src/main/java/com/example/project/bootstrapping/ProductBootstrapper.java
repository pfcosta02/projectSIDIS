package com.example.project.bootstrapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
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
    private ProductRepository productRepo;

    @Override
    public void run(String... args) throws Exception {
        if (productRepo.findByName("Maçarico").isEmpty()) {
            Product f2 = new Product("Maçarico");
            f2.setDescription("O melhor fogo que irá ver");
            f2.setSku("9876543210");
            productRepo.save(f2);
        }
    }

}
