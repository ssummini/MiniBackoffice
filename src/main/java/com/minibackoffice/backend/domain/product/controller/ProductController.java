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
    @AdminOnly
    @PostMapping
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

        Product saved = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponse(saved)); // 201
    }

    // 전체 조회 (누구나)
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {

        List<Product> products = productService.findAll();

        List<ProductResponse> result = new ArrayList<>();
        for (Product p : products) {
            result.add(new ProductResponse(p));
        }

        return ResponseEntity.ok(result); // 200
    }
    
    // 단건 조회 (누구나)
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {

        Product product = productService.findById(id);
        return ResponseEntity.ok(new ProductResponse(product)); // 200
    }

    // 수정 (ADMIN만)
    @AdminOnly
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest req
    ) {
        Product updated = productService.update(id, req);
        return ResponseEntity.ok(new ProductResponse(updated));
    }

    // 삭제 (ADMIN만)
    @AdminOnly
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
