package com.example.project.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.example.project.model.AggregatedRating;
import com.example.project.model.Product;
import com.example.project.model.ProductDTO;
import com.example.project.views.ProductAllView;
import com.example.project.views.ProductNameView;
import org.springframework.http.ResponseEntity;

public interface ProductService {

    List<ProductAllView> findAll();
    Optional<Product> findOne(Long productId);
    Optional<ProductDTO> findBySku(String sku);
    List<ProductNameView> findByName(String name);
    Product create(Product product);
    AggregatedRating getProductRating(Long productId) throws IOException, InterruptedException;
    void addImage(String filename, Long id);
}
