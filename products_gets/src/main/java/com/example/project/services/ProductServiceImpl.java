package com.example.project.services;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.project.model.*;
import com.example.project.repositories.ReviewRepository;
import com.example.project.views.ProductAllView;
import com.example.project.views.ProductNameView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.project.repositories.ProductRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<ProductDTO> findAll() {
        List<Product> allProducts = repository.findAllProducts();
        List<ProductDTO> allProductsDto = new ArrayList<>();

        for(int i=0; i < allProducts.size(); i++) {
            ProductDTO product = new ProductDTO(allProducts.get(i).getName(), allProducts.get(i).getSku(), allProducts.get(i).getDescription(), allProducts.get(i).getSetOfImages());
            allProductsDto.add(product);
        }

        return allProductsDto;
    }

    @Override
    public Optional<ProductDTO> findBySku(final String sku) {
        final var optionalProduct = repository.findBySku(sku);

        if (optionalProduct.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }

        Product p = optionalProduct.get();

        ProductDTO dto = new ProductDTO(p.getName(), p.getSku(), p.getDescription(), p.getSetOfImages());

        return Optional.of(dto);
    }

    @Override
    public  Optional<ProductDTO> findByName(final String name) {
        final var optionalProduct = repository.findByName(name);

        if (optionalProduct.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }

        Product p = optionalProduct.get();

        ProductDTO dto = new ProductDTO(p.getName(), p.getSku(), p.getDescription(), p.getSetOfImages());

        return Optional.of(dto);
    }

    @Override
    public AggregatedRating getProductRating(String productSku) {

        AggregatedRating aggregatedRating = null;

        List<Review> reviews = reviewRepository.findApprovedReviewsByDate(productSku);
        List<ReviewDTO> reviewsDTO = new ArrayList<>();

        for(int i=0; i < reviews.size(); i++) {
            ReviewDTO reviewDTO = new ReviewDTO(reviews.get(i).getReviewId(),reviews.get(i).getText(),reviews.get(i).getRating(),reviews.get(i).getUpVote(),reviews.get(i).getDownVote(),reviews.get(i).getDataTime(),reviews.get(i).getStatus(),reviews.get(i).getProductSku(),reviews.get(i).getCustomerId(),reviews.get(i).getFunnyFact(),reviews.get(i).getVersion());
            reviewsDTO.add(reviewDTO);
        }

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

        for(ReviewDTO r: reviewsDTO){
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

        aggregatedRating = new AggregatedRating(df2.format(oneStarPercentage) + "%",df2.format(twoStarPercentage) + "%",df2.format(threeStarPercentage) + "%",df2.format(fourStarPercentage) + "%",df2.format(fiveStarPercentage) + "%",df.format(media));

        return aggregatedRating;
    }
}
