package com.example.project.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.project.model.AggregatedRating;
import com.example.project.model.ProductDTO;
import com.example.project.usermanagement.model.Role;
import com.example.project.views.ProductAllView;
import com.example.project.views.ProductNameView;
import com.example.project.services.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.project.model.Product;
import com.example.project.services.ProductService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Tag(name = "Products", description = "Endpoints for managing products")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    @Autowired
    private FileStorageService fileStorageService;

    @Operation(summary = "Shows catalog of products")
    @GetMapping
    public List<ProductDTO> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Search for a product by his sku")
    @GetMapping(value = "/sku/{sku}")
    public ResponseEntity<ProductDTO> findBySku(@PathVariable(value = "sku" )String sku)  {
        final var product = service.findBySku(sku)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

        return ResponseEntity.ok().body(product);
    }

    @Operation(summary = "Search for a product by his name")
    @GetMapping(value = "/name/{productName}")
    public ResponseEntity<ProductDTO> findByName(@PathVariable(value =  "productName" )String productName) {
        final var product = service.findByName(productName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

        return ResponseEntity.ok().body(product);
    }

    @Operation(summary = "Create a product")
    @RolesAllowed(Role.ADMIN)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> createProduct(@Valid @RequestBody final Product productId) {
        final var product = service.create(productId);
        return ResponseEntity.ok().eTag(Long.toString(product.getVersion())).body(product);
    }

    @Operation(summary = "Uploads a photo of a product")
    @RolesAllowed(Role.ADMIN)
    @PostMapping("/{productId}/photo")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadFileResponse uploadFile(@PathVariable("productId") final Long productId,
                                         @RequestParam("file") final MultipartFile file) {

        final String fileName = fileStorageService.storeFile(productId.toString(), file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(fileName)
                .toUriString();
        fileDownloadUri = fileDownloadUri.replace("/photos/", "/photo/");

        // TODO save info of the file on the database
        service.addImage(fileName,productId);

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @Operation(summary = "Uploads a set of photos of a product")
    @RolesAllowed(Role.ADMIN)
    @PostMapping("/{productId}/photos")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UploadFileResponse> uploadMultipleFiles(@PathVariable("productId") final Long productId,
                                                        @RequestParam("files") final MultipartFile[] files) {
        return Arrays.asList(files).stream().map(f -> uploadFile(productId, f)).collect(Collectors.toList());
    }

    @Operation(summary = "Search for a rating of a product")
    @GetMapping(value = "/{productId}/rating")
    public ResponseEntity<AggregatedRating> getProductRating(@PathVariable("productId") final String sku) throws IOException, InterruptedException {

        final var product = service.findBySku(sku)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

        AggregatedRating aggregatedRating = service.getProductRating(sku);

        return ResponseEntity.ok().body(aggregatedRating);
    }

}
