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

    Product create(Product product) throws IOException, InterruptedException;
    void addImage(String filename, String sku) throws IOException, InterruptedException;
}
