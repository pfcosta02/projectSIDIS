package com.example.register.services;

import com.example.register.model.App;

import java.util.List;
import java.util.Optional;

public interface AppService {

    List<App> findAll();

    App create(App app);
}
