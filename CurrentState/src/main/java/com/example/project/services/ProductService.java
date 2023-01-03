package com.example.project.services;

import com.example.project.model.AggregatedRating;
import com.example.project.model.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> findAll();
    Optional<ProductDTO> findBySku(String sku);
    Optional<ProductDTO> findByName(String name);
    AggregatedRating getProductRating(String sku);
}
