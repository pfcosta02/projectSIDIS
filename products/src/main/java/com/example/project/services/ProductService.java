package com.example.project.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.example.project.model.AggregatedRating;
import com.example.project.model.Product;
import com.example.project.model.ProductDTO;
import com.example.project.views.ProductAllView;
import com.example.project.views.ProductNameView;

public interface ProductService {

    List<ProductDTO> findAll() throws IOException, InterruptedException;
    Optional<ProductDTO> findBySku(String sku) throws IOException, InterruptedException;
    Optional<ProductDTO> findByName(String name) throws IOException, InterruptedException;
    Product create(Product product);
    AggregatedRating getProductRating(String productSku) throws IOException, InterruptedException;
    void addImage(String filename, Long id);
}
