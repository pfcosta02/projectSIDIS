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
import com.example.project.views.ProductAllView;
import com.example.project.views.ProductNameView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
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

    @Override
    public Product create(final Product product) throws IOException, InterruptedException {
        // construct a new object based on data received by the service to ensure domain
        // invariants are met
        String url = "http://localhost:8090/api/products/sku/" + product.getSku();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product already exists");
        }

        final Product obj = Product.newFrom(product);

        return repository.save(obj);
    }

    @Override
    public void addImage(String filename, String sku) throws IOException, InterruptedException {

        try {
            String url = "http://localhost:8090/api/products/sku/" + sku;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exists");
            }

            ObjectMapper mapper = new ObjectMapper();

            Product product = mapper.readValue(response.body(), Product.class);

            product.addImages(filename);
            repository.save(product);
        } catch (InterruptedException | IOException e) {
        e.printStackTrace();
    }

    }

}
