package com.example.project.repositories;
import java.util.List;
import java.util.Optional;

import com.example.project.model.ProductDTO;
import com.example.project.views.ProductAllView;
import com.example.project.views.ProductNameView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.project.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT f FROM Product f" )
    List<Product> findAllProducts();

    @Query("SELECT f FROM Product f WHERE f.name = ?1")
    Optional<Product> findByName(String name);

    @Query("SELECT f AS sku FROM Product f WHERE f.sku = ?1")
    Optional<Product> findBySku(String sku);



}
