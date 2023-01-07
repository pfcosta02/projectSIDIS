package com.example.project.model;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.StaleObjectStateException;

@Schema(description = "A Product")
@Entity
public class Product {
    // database primary key
    // since this field is autogenerated by the database there is not setId()
    // method, only a getId() method
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Schema(description = "The name of the product")
    @Column (name = "name", length = 50)
    private String name;

    @Column (name = "description")
    private String description;

    @ElementCollection
    @Column(name = "images")
    private List<String> images;

    @Version
    private Long version;

    @Column (name = "sku", nullable = false)
    private String sku;

    protected Product() {}

    public Product(final String name) {
        setName(name);
    }

    public Product(Long productId, String name, String description, String sku) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.sku = sku;
    }

    public Product(String sku, String description, String name) {
        this.sku = sku;
        this.name = name;
        this.description = description;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSku() {
        return sku;
    }

    // for generating the etag
    public Long getVersion() {
        return version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public static Product newFrom(final Product resource) {
        final Product obj = new Product();
        if (resource.getName() != null) {
            obj.setName(resource.name);
        }
        if (resource.getDescription() != null) {
            obj.setDescription(resource.description);
        }
        if (resource.getSku() != null) {
            obj.setSku(resource.sku);
        }
        if(resource.getSetOfImages() != null){
            obj.setSetOfImages(resource.images);
        }
        return obj;
    }

    public void addImages(String filename) {
        images.add(filename);
    }

    public List<String> getSetOfImages() {
        return images;
    }

    public void setSetOfImages(List<String> setOfImages) {
        this.images = setOfImages;
    }
}
