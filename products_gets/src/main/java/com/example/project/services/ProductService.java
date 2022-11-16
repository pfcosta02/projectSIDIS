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

    List<ProductDTO> findAll();
    Optional<ProductDTO> findBySku(String sku);
    Optional<ProductDTO> findByName(String name);
    AggregatedRating getProductRating(String productSku);

}
