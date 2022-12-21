package com.example.project.services;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.project.model.AggregatedRating;
import com.example.project.model.ProductDTO;
import com.example.project.model.ReviewDTO;
import com.example.project.rabbitmq.Sender;
import com.example.project.views.ProductAllView;
import com.example.project.views.ProductNameView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.project.model.Product;
import com.example.project.repositories.ProductRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private Sender sender;

    public String exchange = "product_fanout";

    @Override
    public Product create(final Product product) {
        // construct a new object based on data received by the service to ensure domain
        // invariants are met

        final var optionalProduct = repository.findBySku(product.getSku());

        if(optionalProduct.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product already exists");
        }

        final Product obj = Product.newFrom(product);

        sender.send(exchange,obj);

        return repository.save(obj);
    }

    @Override
    public void addImage(String filename, String sku) {
        final var optionalProduct = repository.findBySku(sku);

        if(optionalProduct.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product dont exists");
        }

        optionalProduct.get().addImages(filename);
        repository.save(optionalProduct.get());
    }

}
