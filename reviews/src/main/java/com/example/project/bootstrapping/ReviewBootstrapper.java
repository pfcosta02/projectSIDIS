package com.example.project.bootstrapping;

import com.example.project.rabbitmq.Rpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("bootstrap")
public class ReviewBootstrapper implements CommandLineRunner {
    // setName setDescription setSku
    @Autowired
    private Rpc rpc;

    @Override
    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void run(String... args) {
        rpc.helper();
        rpc.helper2();
        rpc.helper3();
    }

}
