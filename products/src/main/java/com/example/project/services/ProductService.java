package com.example.project.services;

import java.util.List;
import java.util.Optional;

import com.example.project.model.Product;
import com.example.project.model.ProductDTO;
import com.example.project.views.ProductAllView;
import com.example.project.views.ProductNameView;

public interface ProductService {

    List<ProductAllView> findAll();
    Optional<Product> findOne(Long productId);
    List<ProductNameView> findBySku(String sku);
    List<ProductNameView> findByName(String name);
    Product create(Product product);

    void addImage(String filename, Long id);
}
