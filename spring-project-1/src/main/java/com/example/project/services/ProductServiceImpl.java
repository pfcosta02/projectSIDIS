package com.example.project.services;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import com.example.project.model.AggregatedRating;
import com.example.project.model.Review;
import com.example.project.repositories.ReviewRepository;
import com.example.project.views.ProductAllView;
import com.example.project.views.ProductNameView;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.project.model.Product;
import com.example.project.repositories.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<ProductAllView> findAll() {
        return repository.findAllProducts();
    }

    @Override
    public Optional<Product> findOne(final Long productId) {
        return repository.findById(productId);
    }

    @Override
    public List<ProductNameView> findBySku(final String sku) {
        return repository.findBySku(sku);
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

    @Override
    public AggregatedRating getProductRating(Long productId) {

        Iterable<Review> reviews = reviewRepository.findApprovedReviews(productId);

        int oneStarCounter = 0;
        int twoStarCounter = 0;
        int threeStarCounter = 0;
        int fourStarCounter = 0;
        int fiveStarCounter = 0;
        float oneStarPercentage = 0;
        float twoStarPercentage = 0;
        float threeStarPercentage= 0;
        float fourStarPercentage = 0;
        float fiveStarPercentage = 0;
        float media = 0;
        int soma = 0;
        int total = 0;

        for(Review r: reviews){
            total++;
            soma = soma + r.getRating();

            switch (r.getRating()) {
                case 1:
                    oneStarCounter++;
                    break;
                case 2:
                    twoStarCounter++;
                    break;
                case 3:
                    threeStarCounter++;
                    break;
                case 4:
                    fourStarCounter++;
                    break;
                case 5:
                    fiveStarCounter++;
                    break;
                default:
                    break;
            }

        }

        if(total == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product dont have reviews");
        }

        media = (float) soma / total;

        DecimalFormat df = new DecimalFormat("#.0");
        DecimalFormat df2 = new DecimalFormat("#");

        oneStarPercentage = ((float) oneStarCounter / total) * 100;
        twoStarPercentage = ((float) twoStarCounter / total) * 100;
        threeStarPercentage = ((float) threeStarCounter / total) * 100;
        fourStarPercentage =  ((float) fourStarCounter / total) * 100;
        fiveStarPercentage =   ((float) fiveStarCounter / total) * 100;

        AggregatedRating aggregatedRating = new AggregatedRating(df2.format(oneStarPercentage) + "%",df2.format(twoStarPercentage) + "%",df2.format(threeStarPercentage) + "%",df2.format(fourStarPercentage) + "%",df2.format(fiveStarPercentage) + "%",df.format(media));

        return aggregatedRating;
    }
}
