package com.example.project.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.project.rabbitmq.Sender;
import com.example.project.usermanagement.model.Role;
import com.example.project.services.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Operation(summary = "Create a product")
    @RolesAllowed(Role.ADMIN)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> createProduct(@Valid @RequestBody final Product productId) {
        final var product = service.create(productId);
        return ResponseEntity.status(HttpStatus.CREATED).eTag(Long.toString(product.getVersion())).body(product);
    }

    @Operation(summary = "Uploads a photo of a product")
    @RolesAllowed(Role.ADMIN)
    @PostMapping("/{sku}/photo")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadFileResponse uploadFile(@PathVariable("product sku") final String sku,
                                         @RequestParam("file") final MultipartFile file) {

        final String fileName = fileStorageService.storeFile(sku, file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(fileName)
                .toUriString();
        fileDownloadUri = fileDownloadUri.replace("/photos/", "/photo/");

        try {
            // TODO save info of the file on the database
            service.addImage(fileName,sku);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @Operation(summary = "Uploads a set of photos of a product")
    @RolesAllowed(Role.ADMIN)
    @PostMapping("/{sku}/photos")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UploadFileResponse> uploadMultipleFiles(@PathVariable("productsku") final String sku,
                                                        @RequestParam("files") final MultipartFile[] files) {
        return Arrays.asList(files).stream().map(f -> uploadFile(sku, f)).collect(Collectors.toList());
    }

}
