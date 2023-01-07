package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductDTO {
    public final String sku;
    public final String name;
    public final String description;

    public ProductDTO (@JsonProperty("sku") String sku,
                       @JsonProperty("name") String name,
                       @JsonProperty("description") String description) {
        this.sku = sku;
        this.description = description;
        this.name = name;
    }

}
