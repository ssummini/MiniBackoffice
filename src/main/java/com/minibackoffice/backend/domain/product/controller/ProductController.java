package com.minibackoffice.backend.domain.product.controller;

import com.minibackoffice.backend.domain.product.Product;
import com.minibackoffice.backend.domain.product.dto.ProductCreateRequest;
import com.minibackoffice.backend.domain.product.dto.ProductResponse;
import com.minibackoffice.backend.domain.product.dto.ProductUpdateRequest;
import com.minibackoffice.backend.domain.product.repository.ProductRepository;
import com.minibackoffice.backend.domain.product.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.minibackoffice.backend.global.auth.AdminOnly;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 등록 (ADMIN만)
    @PostMapping
    @AdminOnly
    public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody ProductCreateRequest req
    ) {
        Product product = new Product(
                req.name,
                req.price,
                req.stockQuantity,
                req.status,
                req.thumbnailUrl
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.save(product));
    }

    // 전체 조회 (누구나)
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
    
    // 단건 조회 (누구나)
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    // 수정 (ADMIN만)
    @PutMapping("/{id}")
    @AdminOnly
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest req
    ) {
        return ResponseEntity.ok(productService.update(id, req));
    }

    // 삭제 (ADMIN만)
    @AdminOnly
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
