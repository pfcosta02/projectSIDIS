package com.example.project.model;

import java.util.List;

public class ProductDTO {
    private String sku;
    private String name;
    private String description;
    private List<String> images;

    public ProductDTO(String sku, String name, String description, List<String> images) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.images = images;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
