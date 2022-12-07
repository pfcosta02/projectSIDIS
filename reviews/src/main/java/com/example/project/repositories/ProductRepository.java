package com.example.project.repositories;

import com.example.project.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT f FROM Product f" )
    List<Product> findAllProducts();

    @Query("SELECT f FROM Product f WHERE f.name = ?1")
    Optional<Product> findByName(String name);

    @Query("SELECT f AS sku FROM Product f WHERE f.sku = ?1")
    Optional<Product> findBySku(String sku);


}
