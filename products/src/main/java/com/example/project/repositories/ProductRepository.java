package com.example.project.repositories;
import java.util.List;
import java.util.Optional;

import com.example.project.views.ProductAllView;
import com.example.project.views.ProductNameView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.project.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT f.productId AS productId, f.name AS name FROM Product f" )
    List<ProductAllView> findAllProducts();

    @Query("SELECT f.productId AS productId, f.name AS name, f.description AS description, f.sku AS sku FROM Product f WHERE f.name = ?1")
    List<ProductNameView> findByName(String name);

    @Query("SELECT f.productId AS productId, f.name AS name, f.description AS description, f.sku AS sku FROM Product f WHERE f.sku = ?1")
    Optional<Product> findBySku(String sku);


}
