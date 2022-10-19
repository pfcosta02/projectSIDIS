package com.example.register.services;

import com.example.register.model.App;
import com.example.register.repositories.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class AppServiceImp implements AppService {
    @Autowired
    private AppRepository repository;

    @Override
    public List<App> findAll() {
        return repository.findAll();
    }

    @Override
    public App create(final App app) {
        // construct a new object based on data received by the service to ensure domain
        // invariants are met
        final App obj = App.newFrom(app);

        return repository.save(obj);
    }
}
