package com.example.project.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.project.model.AggregatedRating;
import com.example.project.model.ProductDTO;
import com.example.project.usermanagement.model.Role;
import com.example.project.views.ProductAllView;
import com.example.project.views.ProductNameView;
import com.example.project.services.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.project.model.Product;
import com.example.project.services.ProductService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Tag(name = "Products", description = "Endpoints for managing products")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    @Autowired
    private FileStorageService fileStorageService;

    @Operation(summary = "Shows catalog of products")
    @GetMapping
    public List<ProductDTO> findAll() throws IOException, InterruptedException {
        return service.findAll();
    }


    @Operation(summary = "Search for a product by his sku")
    @GetMapping(value = "/sku/{sku}")
    public ResponseEntity<ProductDTO> findBySku(@PathVariable(value = "sku" )String sku) {
        final var product = service.findBySku(sku)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

        return ResponseEntity.ok().body(product);
    }

    @Operation(summary = "Search for a product by his name")
    @GetMapping(value = "/name/{productName}")
    public ResponseEntity<ProductDTO> findByName(@PathVariable(value =  "productName" )String productName) {
        final var product = service.findByName(productName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

        return ResponseEntity.ok().body(product);
    }

    @Operation(summary = "Search for a rating of a product")
    @GetMapping(value = "/{productSku}/rating")
    public ResponseEntity<AggregatedRating> getProductRating(@PathVariable("productSku") final String productSku) {

        final var product = service.findBySku(productSku)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

        AggregatedRating aggregatedRating = service.getProductRating(productSku);

        return ResponseEntity.ok().body(aggregatedRating);
    }

}
