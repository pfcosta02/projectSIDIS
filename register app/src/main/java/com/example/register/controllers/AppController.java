package com.example.register.controllers;

import com.example.register.model.App;
import com.example.register.services.AppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Application Registry", description = "Endpoints for Register Applications")
@RestController
@RequestMapping("/api/apps")
public class AppController {

    @Autowired
    private AppService service;

    @Operation(summary = "Shows All Apps")
    @GetMapping
    public List<App> findAll() {
        return service.findAll();
    }


    @Operation(summary = "Register an app")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<App> createProduct(@Valid @RequestBody final App appId) {
        final var app = service.create(appId);
        return ResponseEntity.ok().eTag(Long.toString(app.getVersion())).body(app);
    }
}
