package com.example.project.services;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import com.example.project.views.ProductAllView;
import com.example.project.views.ProductNameView;
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
    public List<ProductAllView> findAll() {
        return repository.findAllProducts();
    }

    @Override
    public Optional<Product> findOne(final Long productId) {
        return repository.findById(productId);
    }

    @Override
    public List<ProductNameView> findByName(final String name) {
        return repository.findByName(name);
    }

    @Override
    public Product create(final Product product) {
        // construct a new object based on data received by the service to ensure domain
        // invariants are met
        final Product obj = Product.newFrom(product);

        return repository.save(obj);
    }

    @Override
    public void addImage(String filename, Long id) {
        Optional<Product> optionalProduct = repository.findById(id);

        if (!optionalProduct.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }
        optionalProduct.get().addImages(filename);
        repository.save(optionalProduct.get());
    }
}
