package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductDTO {
    public final String sku;
    public final String name;
    public final String description;
    public List<String> images;

    public ProductDTO (
                       @JsonProperty("name") String name,
                       @JsonProperty("sku") String sku,
                       @JsonProperty("description") String description,
                       @JsonProperty("images")  List<String> images) {
        this.sku = sku;
        this.description = description;
        this.name = name;
        this.images = images;
    }

}
