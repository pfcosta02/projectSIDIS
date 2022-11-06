package com.example.project.services;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.project.model.AggregatedRating;
import com.example.project.model.ProductDTO;
import com.example.project.model.ReviewDTO;
import com.example.project.views.ProductAllView;
import com.example.project.views.ProductNameView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductAllView> findAll() {
        return repository.findAllProducts();
    }

    @Override
    public Optional<Product> findOne(final Long productId) throws IOException, InterruptedException {
        Optional<Product> optionalProduct = repository.findById(productId);

        if (optionalProduct.isEmpty()){

            /*
            String url = "http://localhost:8084/api/products/" + productId;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());


            ObjectMapper mapper = new ObjectMapper();

            optionalProduct = mapper.readValue(response.body(), new TypeReference<Optional<Product>>() {});

             */
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }

        return optionalProduct;
    }

    @Override
    public  List<ProductNameView> findBySku(final String sku) throws IOException, InterruptedException {
        List<ProductNameView> product = repository.findBySku(sku);

        if (product.isEmpty()) {
            String url = "http://localhost:8084/api/products/sku/" + sku;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();

           // product = mapper.readValue(response.body(), new TypeReference<List<ProductNameView>>(){});

        }

        return product;
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

        if (optionalProduct.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }
        optionalProduct.get().addImages(filename);
        repository.save(optionalProduct.get());
    }

    @Override
    public AggregatedRating getProductRating(Long productId) throws IOException, InterruptedException {

        String url = "http://localhost:8082/api/reviews/product/" + productId + "/date";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product as no reviews");
        }

        ObjectMapper mapper = new ObjectMapper();

        List<ReviewDTO> reviews = mapper.readValue(response.body(), new TypeReference<List<ReviewDTO>>() {});

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

        for(ReviewDTO r: reviews){
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
