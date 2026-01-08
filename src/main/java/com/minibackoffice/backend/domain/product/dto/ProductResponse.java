package com.minibackoffice.backend.domain.product.dto;

import com.minibackoffice.backend.domain.product.Product;

public class ProductResponse {

    public Long id;
    public String name;
    public Integer price;
    public Integer stockQuantity;
    public String status;
    public String thumbnailUrl;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.status = product.getStatus();
        this.thumbnailUrl = product.getThumbnailUrl();
    }
}
