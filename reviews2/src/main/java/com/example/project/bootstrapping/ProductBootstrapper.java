package com.example.project.bootstrapping;


import com.example.project.model.Product;
import com.example.project.repositories.ProductRepository;
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
public class ProductBootstrapper implements CommandLineRunner {
// setName setDescription setSku
    @Autowired
    private ProductRepository productRepo;

    public void run(String... args) throws Exception {
        if (productRepo.findByName("Martelo").isEmpty()) {
            Product f1 = new Product("Martelo");
            f1.setDescription("Aço carbono");
            f1.setSku("123456789A");
            productRepo.save(f1);
        }

        if (productRepo.findByName("Maçarico").isEmpty()) {
            Product f2 = new Product("Maçarico");
            f2.setDescription("O melhor fogo que irá ver");
            f2.setSku("9876543210");
            productRepo.save(f2);
        }
    }

}
