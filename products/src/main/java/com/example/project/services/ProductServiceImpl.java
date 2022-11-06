package com.example.project.services;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.project.model.AggregatedRating;
import com.example.project.model.ProductDTO;
import com.example.project.model.ReviewDTO;
import com.example.project.views.ProductAllView;
import com.example.project.views.ProductNameView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
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

    @Override
    public List<ProductDTO> findAll() {
        List<Product> allProducts = repository.findAllProducts();
        List<ProductDTO> allProductsDto = new ArrayList<>();

        for(int i=0; i < allProducts.size(); i++) {
            ProductDTO product = new ProductDTO(allProducts.get(i).getName(), allProducts.get(i).getSku(), allProducts.get(i).getDescription(), allProducts.get(i).getSetOfImages());
            allProductsDto.add(product);
        }

        try {
            String url = "http://localhost:8084/api/products";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
            }

            ObjectMapper mapper = new ObjectMapper();
            List<ProductDTO> products = mapper.readValue(response.body(), new TypeReference<List<ProductDTO>>() {});

            for(int i=0; i < products.size(); i++) {
                allProductsDto.add(products.get(i));
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return allProductsDto;
    }


    @Override
    public Optional<ProductDTO> findBySku(final String sku) {
        final var optionalProduct = repository.findBySku(sku);

        if (optionalProduct.isEmpty()){
            try {
                String url = "http://localhost:8084/api/products/sku/" + sku;

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();

                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());

                if(response.statusCode() != 200) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
                }

                ObjectMapper mapper = new ObjectMapper();

                ProductDTO dto = mapper.readValue(response.body(), ProductDTO.class);

                return Optional.of(dto);

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }

        }

        Product p = optionalProduct.get();

        ProductDTO dto = new ProductDTO(p.getSku(), p.getName(), p.getDescription(), p.getSetOfImages());

        return Optional.of(dto);
    }

    @Override
    public  Optional<ProductDTO> findByName(final String name) {
        final var optionalProduct = repository.findByName(name);

        if (optionalProduct.isEmpty()){
            try {
                String url = "http://localhost:8084/api/products/name/" + name;

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();

                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());

                if(response.statusCode() != 200) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
                }

                ObjectMapper mapper = new ObjectMapper();

                ProductDTO dto = mapper.readValue(response.body(), ProductDTO.class);

                return Optional.of(dto);

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }

        }

        Product p = optionalProduct.get();

        ProductDTO dto = new ProductDTO(p.getName(), p.getSku(), p.getDescription(), p.getSetOfImages());

        return Optional.of(dto);
    }

    @Override
    public Product create(final Product product) {
        // construct a new object based on data received by the service to ensure domain
        // invariants are met
        final var optionalProduct = repository.findBySku(product.getSku());

        if (optionalProduct.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product sku already exists");
        }

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
    public AggregatedRating getProductRating(String productSku) {

        AggregatedRating aggregatedRating = null;

        try {
            String url = "http://localhost:8082/api/reviews/product/" + productSku + "/date";

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

            aggregatedRating = new AggregatedRating(df2.format(oneStarPercentage) + "%",df2.format(twoStarPercentage) + "%",df2.format(threeStarPercentage) + "%",df2.format(fourStarPercentage) + "%",df2.format(fiveStarPercentage) + "%",df.format(media));

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return aggregatedRating;
    }
}
