package com.minibackoffice.backend.domain.product;

import com.minibackoffice.backend.global.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false, length = 20)
    private String status; // SALE / SOLD_OUT / HIDDEN (나중에 enum으로 바꿔도 됨)

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    protected Product() { } // JPA 기본 생성자

    public Product(String name, Integer price, Integer stockQuantity, String status, String thumbnailUrl) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.status = status;
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }


    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getPrice() { return price; }
    public Integer getStockQuantity() { return stockQuantity; }
    public String getStatus() { return status; }
    public String getThumbnailUrl() { return thumbnailUrl; }
}
